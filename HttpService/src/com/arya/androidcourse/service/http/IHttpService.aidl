// This file is IHTTPService.aidl
package com.arya.androidcourse.service.http;
import com.arya.androidcourse.service.http.ParseableByteArray;

interface IHttpService
{
    String getTextContent(String url);
    
    ParseableByteArray getImageContent(String url);
}