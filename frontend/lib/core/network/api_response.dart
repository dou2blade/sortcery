class ApiResponse<T> {
  final int status;
  final String? error;
  final String? message;
  final String? path;
  final String? timestamp;
  final T? data;
  final Pagination? pagination;

  ApiResponse({
    required this.status,
    this.error,
    this.message,
    this.path,
    this.timestamp,
    this.data,
    this.pagination
  });
}

class Pagination {
  final int page;
  final int size;
  final int totalElements;
  final int totalPages;
  final bool last;

  Pagination({
    required this.page,
    required this.size,
    required this.totalElements,
    required this.totalPages,
    required this.last
  });
}

