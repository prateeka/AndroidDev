// This file is IHTTPService.aidl
package com.arya.androidcourse.service.http;

interface IHttpService
{
    String getTextContent(String url);
    
    String getImageContent(String url);
}