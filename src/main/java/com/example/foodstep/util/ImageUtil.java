package com.example.foodstep.util;

public class ImageUtil {

    //S3 Domain
    private static final String S3_URL_PREFIX = "https://foodstep-image-s3-bucket.s3.ap-northeast-2.amazonaws.com/";

    //CloudFront Domain
    private static final String CDN_URL_PREFIX = "https://d21qmne7l7o0to.cloudfront.net/";

    private static String getImagePathS3(String imagePath) {
        return S3_URL_PREFIX + imagePath;
    }

    private static String getImagePathCdn(String imagePath) {
        return CDN_URL_PREFIX + imagePath;
    }
}
