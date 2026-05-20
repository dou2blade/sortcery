import 'package:sortcery/core/enums/user_role.dart';
import 'package:sortcery/core/network/api_client.dart';
import 'package:sortcery/core/network/api_result.dart';
import 'package:sortcery/features/auth/data/dto/login_request.dart';
import 'package:sortcery/features/auth/data/dto/login_response.dart';

class AuthApi {
  final ApiClient apiClient;

  AuthApi(this.apiClient);

  Future<ApiResult<LoginResponse>> login(LoginRequest req, UserRole role) {
    return apiClient.post(
      resource: 'auth/login/${role.name}', 
      payload: req.toJson(),
      includeToken: false,
      parser: (json) => LoginResponse.fromJson(json)
    );
  }
}
