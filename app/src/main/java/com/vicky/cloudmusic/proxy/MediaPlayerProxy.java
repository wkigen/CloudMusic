package com.vicky.cloudmusic.proxy;

import com.vicky.android.baselib.http.callback.FileCallBack;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.android.baselib.utils.ILog;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.net.Net;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by vicky on 2017/9/6.
 */
public class MediaPlayerProxy implements Runnable{

    public static final String TAG = MediaPlayerProxy.class.getSimpleName();

    private Thread thread;
    private int port;
    private boolean isStart = false;
    private ServerSocket proxyServerSocket;

    public MediaPlayerProxy(){
        try {
            proxyServerSocket = new ServerSocket(port, 0, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
            proxyServerSocket.setSoTimeout(5000);
            port = proxyServerSocket.getLocalPort();
            thread = new Thread(this);
        } catch (Exception e) {
            ILog.e(TAG,"MediaPlayerProxy init is error");
        }
    }

    //返回本地url
    public String getLoacalUrl(String url,String fileName){
        return String.format("http://127.0.0.1:%d/%s/%s", port, url,fileName);
    }

    @Override
    public void run() {

        while (isStart){
            try{
                Socket socket = proxyServerSocket.accept();
                if (socket == null)
                    continue;
                handle(socket);
            }catch (Exception e){
            }

        }
    }

    public void start(){
        isStart = true;
        if (thread != null)
            thread.start();
    }

    public void stop(){
        if (thread != null)
            isStart = false;
    }


    public void handle(final Socket socket) throws Exception{

        String requestStr = getRequestHeader(socket);

        String[] requestParts = requestStr.split("\r\n\r\n");
        String[] st = requestParts[0].split(" ");
        String method = st[0];
        String tempUrl = st[1].substring(1);
        int lastSlash = tempUrl.lastIndexOf("/");
        final String fileName = tempUrl.substring(lastSlash+1,tempUrl.length());
        String uri = tempUrl.substring(0, lastSlash);
        //找到Range
        String[] allParams = requestParts[0].split("\r\n");
        String rangeStart = "0";
        String rangeEnd = "";
        for (String str : allParams){
            if (str.contains("Range:")){
                int indexEq = str.indexOf("=");
                String realRange = str.substring(indexEq + 1, str.length());
                String[] ranges = realRange.split("-");
                if (ranges.length == 1){
                    rangeStart = ranges[0];
                    break;
                }else if (ranges.length == 2){
                    rangeStart = ranges[0];
                    rangeEnd = ranges[1];
                    break;
                }
            }
        }

        final String realRangeStart = rangeStart;
        final String realRangeEnd = rangeEnd;
        String requestRange = "bytes="+realRangeStart+"-"+realRangeEnd;
        final String dirPath = CacheManager.getImstance().getDirPath();
        Net.getWyApi().getApi().downFile(requestRange,uri).execute(new FileCallBack(dirPath+"/"+ Constant.temp_dir_name+"/",fileName+".temp") {

            @Override
            public File parseNetworkResponse(Response response, int id) throws Exception {
                String range = response.header("Content-Range");
                String strHead = "";
                if (range == null){
                    strHead = genResponseHeader(0, (int) response.body().contentLength() - 1, (int) response.body().contentLength());
                }else {
                    String[] temps = range.split(" ");
                    temps = temps[1].split("-");
                    String startRange = temps[0];
                    temps = temps[1].split("/");
                    String endRange =temps[0];
                    String len = temps[1];
                    strHead = genResponseHeader(Integer.parseInt(startRange),Integer.parseInt(endRange),Integer.parseInt(len));
                }
                socket.getOutputStream().write(strHead.getBytes());
                return this.saveFile(response, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {

            }

            @Override
            public void readData(byte[] buf,int len,long sum) {
                try{
                    socket.getOutputStream().write(buf,0,len);
                }catch (Exception e){
                }
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                try{
                    socket.close();
                }catch (Exception e1){

                }
            }

            @Override
            public void onResponse(File file, int i) {
                try{
                    //保存的文件是完整的,复制出来
                    if (realRangeStart.equals("0")&&realRangeEnd.equals("")){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FileUtils.coypFile(dirPath+"/"+ Constant.temp_dir_name+"/"+fileName+".temp",dirPath+"/"+fileName);
                            }
                        }).start();
                    }
                    socket.close();
                }catch (Exception e){

                }
            }
        });
    }

    public String getRequestHeader(Socket socket) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];
        int count = -1;
        while ((count = socket.getInputStream().read(buffer)) != -1) {
            byte[] tmpBuffer = new byte[count];
            System.arraycopy(buffer, 0, tmpBuffer, 0, count);
            String str = new String(tmpBuffer);
            ILog.i(TAG + " Header-> ", str);
            stringBuilder.append(str);
            if (stringBuilder.toString().contains("GET") && stringBuilder.toString().contains("\r\n\r\n")) {
                break;
            }
        }
        return stringBuilder.toString();
    }


    public String genResponseHeader(int rangeStart, int rangeEnd, int fileLength) {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 206 Partial Content").append("\n");
        sb.append("Content-Type: audio/mpeg").append("\n");
        sb.append("Content-Length: ").append(rangeEnd - rangeStart + 1).append("\n");
        sb.append("Connection: keep-alive").append("\n");
        sb.append("Accept-Ranges: bytes").append("\n");
        String contentRangeValue = String.format("Rang"+"%d-%d/%d", rangeStart, rangeEnd, fileLength);
        sb.append("Content-Range: ").append(contentRangeValue).append("\n");
        sb.append("\n");
        return sb.toString();
    }

}
