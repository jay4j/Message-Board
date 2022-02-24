package com.concordia.message_board.entities;

import java.sql.Blob;

public class Attachment {
    //public String path;
    private String attachId;
    private String postId;
    //public String userId;
    private String fileName;
    private String fileType;
    private long fileSize;
    private Blob blob;
    //---------------------initialize isEdited as false----------------------
    private boolean isEdited = false;

    public Attachment() {
        fileName = "";
        fileType = "";
    }
   //-------------------------get edited method-------------------------
    public boolean isEdited() {
        return isEdited;
    }
  //--------------------------set edited method-------------------------
    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public Attachment(String attachId, String postId, String fileName, String fileType, long fileSize, Blob blob) {
        //this.path = path;
        this.attachId = attachId;
        this.postId = postId;
        //this.userId = userId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.blob = blob;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Blob getBlob(){
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "Attachment" +
                ", postId='" + postId + '\'' +
                ", file name='" + fileName + '\'' +
                '}';
    }
}
