package com.example.chanyue.bean;
import com.google.gson.annotations.SerializedName;

public class PostVideoResponse {

//luomeiqiu
//    @SerializedName("success") private Boolean success;
//    @SerializedName("item") private  Item item;
//
//    public Boolean getSuccess() {
//        return success;
//    }
//
//    public Item getItem() {
//        return item;
//    }
//
//    public void setItem(Item item) {
//        this.item = item;
//    }
//
//    public void setSuccess(Boolean success) {
//        this.success = success;
//    }
//    public static class Item{
//        @SerializedName("student_id") private String student_id;
//        @SerializedName("user_name") private String user_name;
//        @SerializedName("image_url") private String image_url;
//        @SerializedName("video_url") private String video_url;
//
//        public String getImage_url() {
//            return image_url;
//        }
//
//        public String getStudent_id() {
//            return student_id;
//        }
//
//        public String getUser_name() {
//            return user_name;
//        }
//
//        public String getVideo_url() {
//            return video_url;
//        }
//
//        public void setImage_url(String image_url) {
//            this.image_url = image_url;
//        }
//
//        public void setStudent_id(String student_id) {
//            this.student_id = student_id;
//        }
//
//        public void setUser_name(String user_name) {
//            this.user_name = user_name;
//        }
//
//        public void setVideo_url(String video_url) {
//            this.video_url = video_url;
//        }
//
//        @Override
//        public String toString() {
//            return "Item{" +
//                    "student_id=" + student_id +
//                    ",user_name=" + user_name +
//                    ",image_url=" + image_url +
//                    ",video_url=" + video_url +
//                    "}";
//        }
//    }
//@Override
//    public String toString() {
//        return "FeedResponse{" +
//                "success=" + success +
//                ",item=" + item +
//                "}";
//    }

    @SerializedName("success") private boolean success;
    @SerializedName("result") private Feed result;

    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Feed getFeeds() {
        return result;
    }
    public void setbFeeds(Feed result) {
        this.result = result;
    }
}
