import 'dart:convert';

import 'package:sortcery/core/network/api_response.dart';
import 'package:http/http.dart' as http;

enum HttpMethod {
  get,
  post,
  put,
  delete
}

const defaultHeaders = {
  'Content-Type': 'application/json',
  'Accept': 'application/json'
};

class ApiClient {
  final String apiUrl;

  ApiClient._internal(this.apiUrl);

  factory ApiClient() {
    const url = String.fromEnvironment("API_URL");

    if (url.isEmpty) {
      throw Exception("API_URL is not set. Use --dart-define");
    }

    return ApiClient._internal(url);
  }

  Future<ApiResponse> _fetch(
    HttpMethod method, 
    String resource,
    { Map<String, String>? query, 
    String? payload, 
    bool includeToken = true }
  ) async {
    final uri = Uri.parse(apiUrl).resolveUri(
      Uri(path: resource,queryParameters: query)
    );

    http.Response res;
    final headers = { 
      ...defaultHeaders,
      if (includeToken) 'Authorization': 'Bearer token'
    };

    switch (method) {
      case HttpMethod.get:
        res = await http.get(uri, headers: headers);
        break;
      case HttpMethod.post:
        res = await http.post(uri, headers: headers, body: payload);
        break;
      case HttpMethod.put:
        res = await http.put(uri, headers: headers, body: payload);
        break;
      case HttpMethod.delete:
        res = await http.delete(uri, headers: headers);
        break;
    }

    final body = jsonDecode(res.body);

    if (res.statusCode != 200 && res.statusCode != 201) {
      return ApiResponse(
        status: res.statusCode,
        error: body?['error'],
        path: body?['path'],
        message: body?['message'],
        timestamp: body?['timestamp'],
      );
    }

    return ApiResponse(status: res.statusCode, data: body?['data']);
  }

  Future<ApiResponse> get({
    required String resource,
    Map<String, String>? query,
    bool includeToken = false
  }) async {
    return await _fetch(
      HttpMethod.get, 
      resource, 
      query: query, 
      includeToken: includeToken
    );
  }

  Future<ApiResponse> post({ 
    required String resource,
    Map<String, String>? query,
    String? payload,
    bool includeToken = true
  }) async {
    return await _fetch(
      HttpMethod.post, 
      resource, 
      query: query, 
      payload: payload,
      includeToken: includeToken
    );
  }

  Future<ApiResponse> put({ 
    required String resource,
    Map<String, String>? query,
    String? payload,
    bool includeToken = true
  }) async {
    return await _fetch(
      HttpMethod.put, 
      resource, 
      query: query, 
      payload: payload,
      includeToken: includeToken
    );
  }

  Future<ApiResponse> delete({ 
    required String resource,
    Map<String, String>? query,
    bool includeToken = true
  }) async {
    return await _fetch(
      HttpMethod.delete, 
      resource, 
      query: query, 
      includeToken: includeToken
    );
  }
}
