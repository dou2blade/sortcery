import 'package:sortcery/core/enums/api_status.dart';

class ApiResponse<T> {
  final ApiStatus status;

  final int statusCode;

  final String? error;
  final String? message;
  final String? path;
  final String? timestamp;

  final T? data;
  final Meta? meta;

  ApiResponse({
    required this.status,
    required this.statusCode,
    this.error,
    this.message,
    this.path,
    this.timestamp,
    this.data,
    this.meta,
  });

  bool get isSuccess => status == ApiStatus.success;
  bool get isError => status == ApiStatus.error;
}

class Meta {
  final int page;
  final int size;
  final int totalElements;
  final int totalPages;
  final bool last;

  Meta({
    required this.page,
    required this.size,
    required this.totalElements,
    required this.totalPages,
    required this.last
  });
}

