import 'package:sortcery/core/enums/user_role.dart';
import 'package:sortcery/features/auth/data/dto/login_response.dart';

class AuthSession {
  final int id;
  final String firstName;
  final String lastName;
  final String email;
  final UserRole role;
  final String token;

  AuthSession({
    required this.id,
    required this.firstName,
    required this.lastName,
    required this.email,
    required this.role,
    required this.token
  });

  factory AuthSession.from(LoginResponse res) {
    return AuthSession(
      id: res.id,
      firstName: res.firstName,
      lastName: res.lastName,
      email: res.email,
      role: res.role,
      token: res.plainToken
    );
  }
}
