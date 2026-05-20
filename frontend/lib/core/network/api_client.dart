import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:sortcery/core/auth/token_storage.dart';
import 'package:sortcery/core/network/api_result.dart';

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
  final TokenStorage tokenStorage;

  ApiClient._internal(this.apiUrl, this.tokenStorage);

  factory ApiClient(TokenStorage tokenStorage) {
    const url = String.fromEnvironment("API_URL");

    if (url.isEmpty) {
      throw Exception("API_URL is not set. Use --dart-define");
    }

    return ApiClient._internal(url, tokenStorage);
  }

  Future<ApiResult<T>> _fetch<T>({
    required HttpMethod method, 
    required String resource,
    Map<String, String>? query, 
    String? payload, 
    bool includeToken = true,
    required T Function(dynamic json) parser
  }) async {
    final uri = Uri.parse(apiUrl).resolveUri(
      Uri(path: resource,queryParameters: query)
    );

    http.Response res;

    final token = tokenStorage.getToken();

    final headers = { 
      ...defaultHeaders,
      if (includeToken) 'Authorization': 'Bearer $token'
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

    final Map<String, dynamic> body = jsonDecode(res.body);

    if (res.statusCode < 200 || res.statusCode > 204) {
      return ApiError<T>(
        statusCode: res.statusCode,
        error: body['error'],
        message: body['message'],
      );
    }

    return ApiSuccess<T>(
      data: parser(body['data']),
      meta: body['meta']
    );
  }

  Future<ApiResult<T>> get<T>({ 
    required String resource,
    Map<String, String>? query,
    bool includeToken = false,
    required T Function(dynamic json) parser
  }) {
    return _fetch<T>(
      method: HttpMethod.get, 
      resource: resource, 
      query: query, 
      includeToken: includeToken,
      parser: parser
    );
  }

  Future<ApiResult<T>> post<T>({ 
    required String resource,
    Map<String, String>? query,
    Map<String, dynamic>? payload,
    bool includeToken = true,
    required T Function(dynamic json) parser
  }) {
    return _fetch<T>(
      method: HttpMethod.post, 
      resource: resource, 
      query: query, 
      payload: jsonEncode(payload),
      includeToken: includeToken,
      parser: parser
    );
  }

  Future<ApiResult<T>> put<T>({ 
    required String resource,
    Map<String, String>? query,
    String? payload,
    bool includeToken = true,
    required T Function(dynamic json) parser
  }) {
    return _fetch<T>(
      method: HttpMethod.put, 
      resource: resource, 
      query: query, 
      payload: payload,
      includeToken: includeToken,
      parser: parser
    );
  }

  Future<ApiResult<T>> delete<T>({ 
    required String resource,
    Map<String, String>? query,
    bool includeToken = true,
    required T Function(dynamic json) parser
  }) {
    return _fetch<T>(
      method: HttpMethod.delete, 
      resource: resource, 
      query: query, 
      includeToken: includeToken,
      parser: parser
    );
  }
}
