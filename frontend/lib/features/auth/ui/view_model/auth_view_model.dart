import 'package:flutter/material.dart';
import 'package:sortcery/core/enums/user_role.dart';
import 'package:sortcery/core/network/api_result.dart';
import 'package:sortcery/features/auth/data/auth_repository.dart';
import 'package:sortcery/features/auth/data/dto/login_request.dart';
import 'package:sortcery/features/auth/domain/auth_session.dart';

class AuthViewModel extends ChangeNotifier {
  final AuthRepository authRepository;

  AuthSession? _session;

  AuthSession? get session => _session;
  bool get isLoggedIn => _session != null;
  String? get token => _session?.token;

  AuthViewModel(this.authRepository);

  Future<ApiResult<AuthSession>> login(LoginRequest req, UserRole role) async {
    final res = await authRepository.login(req, role);

    print("HI");
    switch (res) {
      case ApiSuccess<AuthSession>():
        _session = res.data;
        notifyListeners();
        return ApiSuccess<AuthSession>(data: res.data);
      case ApiError<AuthSession>():
        return ApiError<AuthSession>(
          error: res.error, 
          message: res.message, 
          statusCode: res.statusCode
        );
    }
  }

  void logout() {
    _session = null;
    notifyListeners();
  }

  void restore(AuthSession session) {
    _session = session;
    notifyListeners();
  }
}
