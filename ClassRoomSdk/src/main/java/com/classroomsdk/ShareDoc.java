package com.classroomsdk;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/5/19.
 * 文档实体
 */

public class ShareDoc implements Cloneable,Comparable<ShareDoc>{
    private String pdfpath;
    private String filepath;
    private int animation;
    private int status;
    private String downloadpath;
    private int pagenum = 1;
    private String uploadusername;
    private String newfilename;
    private long uploaduserid;
    private String swfpath;
    private long fileid;
    private int isconvert;
    private long size;
    private long companyid;
    private int fileserverid;
    private String uploadtime;
    private int active;
    private String filename;
    private String filetype;
    private int currentPage = 1;
    private boolean isMedia;
    private double currentTime;
    private int pptslide = 1;
    private int pptstep;
    private int steptotal;
    private int fileprop;
    private boolean isGeneralFile;
    private boolean isDynamicPPT;
    private boolean isH5Docment;
    private int filecategory; // 0 可以删除  1 不可以删除

    public int getFilecategory() {
        return filecategory;
    }

    public void setFilecategory(int filecategory) {
        this.filecategory = filecategory;
    }

    public boolean isGeneralFile() {
        return isGeneralFile;
    }

    public void setGeneralFile(boolean generalFile) {
        isGeneralFile = generalFile;
    }

    public boolean isDynamicPPT() {
        return isDynamicPPT;
    }

    public void setDynamicPPT(boolean dynamicPPT) {
        isDynamicPPT = dynamicPPT;
    }

    public boolean isH5Docment() {
        return isH5Docment;
    }

    public void setH5Docment(boolean h5Docment) {
        isH5Docment = h5Docment;
    }

    public int getFileprop() {
        return fileprop;
    }

    public void setFileprop(int fileprop) {
        this.fileprop = fileprop;
    }

    public int getSteptotal() {
        return steptotal;
    }

    public void setSteptotal(int steptotal) {
        this.steptotal = steptotal;
    }

    public int getPptslide() {
        return pptslide;
    }

    public void setPptslide(int pptslide) {
        this.pptslide = pptslide;
    }

    public int getPptstep() {
        return pptstep;
    }

    public void setPptstep(int pptstep) {
        this.pptstep = pptstep;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isMedia() {
        return isMedia;
    }

    public void setMedia(boolean media) {
        isMedia = media;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDownloadpath() {
        return downloadpath;
    }

    public void setDownloadpath(String downloadpath) {
        this.downloadpath = downloadpath;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public String getUploadusername() {
        return uploadusername;
    }

    public void setUploadusername(String uploadusername) {
        this.uploadusername = uploadusername;
    }

    public String getNewfilename() {
        return newfilename;
    }

    public void setNewfilename(String newfilename) {
        this.newfilename = newfilename;
    }

    public long getUploaduserid() {
        return uploaduserid;
    }

    public void setUploaduserid(long uploaduserid) {
        this.uploaduserid = uploaduserid;
    }

    public String getSwfpath() {
        return swfpath;
    }

    public void setSwfpath(String swfpath) {
        this.swfpath = swfpath;
    }

    public long getFileid() {
        return fileid;
    }

    public void setFileid(long fileid) {
        this.fileid = fileid;
    }

    public int getIsconvert() {
        return isconvert;
    }

    public void setIsconvert(int isconvert) {
        this.isconvert = isconvert;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(long companyid) {
        this.companyid = companyid;
    }

    public int getFileserverid() {
        return fileserverid;
    }

    public void setFileserverid(int fileserverid) {
        this.fileserverid = fileserverid;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public ShareDoc clone(){
        ShareDoc o = null;
        try {
            o = (ShareDoc) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }



    @Override
    public int compareTo(@NonNull ShareDoc o) {
        return (int)(this.getFileid()-o.getFileid());
    }
}
