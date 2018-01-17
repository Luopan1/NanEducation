package com.classroomsdk;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/29.
 */

public class Packager {
    public static ShareDoc pageDoc(JSONObject jsdata) {
        ShareDoc doc = new ShareDoc();
        JSONObject jsfiledata = jsdata.optJSONObject("filedata");
        doc.setGeneralFile(Tools.isTure(jsdata.opt("isGeneralFile")));
        doc.setMedia(Tools.isTure(jsdata.opt("isMedia")));
        doc.setDynamicPPT(Tools.isTure(jsdata.opt("isDynamicPPT")));
        doc.setH5Docment(Tools.isTure(jsdata.opt("isH5Document")));
        doc.setFileid(Long.valueOf(jsfiledata.opt("fileid").toString()));
        doc.setCurrentPage(jsfiledata.optInt("currpage"));
        doc.setFiletype(jsfiledata.optString("filetype"));
        doc.setPagenum(jsfiledata.optInt("pagenum"));
        doc.setFilename(jsfiledata.optString("filename"));
        doc.setSwfpath(jsfiledata.optString("swfpath"));
        doc.setPptslide(jsfiledata.optInt("pptslide", 1));
        doc.setPptstep(jsfiledata.optInt("pptstep"));
        doc.setSteptotal(jsfiledata.optInt("steptotal"));
        return doc;
    }

    public static JSONObject pageSendData(ShareDoc doc) {
        JSONObject jsdata = new JSONObject();
        JSONObject filedata = new JSONObject();
        try {
            jsdata.put("isGeneralFile", doc.isGeneralFile());
            jsdata.put("isMedia", doc.isMedia());
            jsdata.put("isDynamicPPT", doc.isDynamicPPT());
            jsdata.put("isH5Document", doc.isH5Docment());
            jsdata.put("action", doc.isDynamicPPT() ? "show" : "");
            jsdata.put("mediaType", doc.isMedia() ? Tools.isMp4(doc.getFilename()) ? "video" : "audio" : "");
            filedata.put("fileid", doc.getFileid());
            filedata.put("currpage", doc.getCurrentPage());
            filedata.put("pagenum", doc.getPagenum());
            filedata.put("filetype", doc.getFiletype());
            filedata.put("filename", doc.getFilename());
            filedata.put("swfpath", doc.getSwfpath());
            filedata.put("pptslide", doc.getPptslide());
            filedata.put("pptstep", doc.getPptstep());
            if (doc.isDynamicPPT()) {
                filedata.put("swfpath", doc.getDownloadpath() == null ? doc.getSwfpath() : doc.getDownloadpath());
            } else {
                filedata.put("swfpath", doc.getSwfpath());
            }
            filedata.put("steptotal", doc.getSteptotal());
            jsdata.put("filedata", filedata);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsdata;
    }
}
