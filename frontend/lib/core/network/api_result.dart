import 'package:sortcery/core/network/api_response.dart';

sealed class ApiResult<T> {}

class ApiSuccess<T> extends ApiResult<T> {
  final T data;
  final Meta? meta;
  ApiSuccess({ required this.data, this.meta });
}

class ApiError<T> extends ApiResult<T> {
  final int statusCode;
  final String error;
  final String message;

  ApiError({
    required this.message,
    required this.error,
    required this.statusCode
  });
}
