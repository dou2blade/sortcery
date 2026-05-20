import 'package:sortcery/core/auth/token_storage.dart';
import 'package:sortcery/core/enums/user_role.dart';
import 'package:sortcery/core/network/api_result.dart';
import 'package:sortcery/features/auth/data/auth_api.dart';
import 'package:sortcery/features/auth/data/dto/login_response.dart';
import 'package:sortcery/features/auth/domain/auth_session.dart';
import 'package:sortcery/features/auth/data/dto/login_request.dart';

class AuthRepository {
  final AuthApi authApi;
  final TokenStorage tokenStorage;

  AuthRepository(this.authApi, this.tokenStorage);

  Future<ApiResult<AuthSession>> login(LoginRequest req, UserRole role) async {
    final res = await authApi.login(req, role);

    switch (res) {
      case ApiSuccess<LoginResponse>():
        final loginRes = res.data;

        await tokenStorage.saveToken(loginRes.plainToken);
        return ApiSuccess<AuthSession>(data: AuthSession.from(loginRes));

      case ApiError<LoginResponse>():
        return ApiError<AuthSession>(
          statusCode: res.statusCode,
          message: res.message,
          error: res.error
        );
    }
  }
}
