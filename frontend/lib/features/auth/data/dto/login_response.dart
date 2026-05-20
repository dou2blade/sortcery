import 'package:sortcery/core/enums/user_role.dart';

class LoginResponse {
  final int id;
  final String firstName;
  final String lastName;
  final String email;
  final UserRole role;
  final String plainToken;

  LoginResponse({
    required this.id,
    required this.firstName,
    required this.lastName,
    required this.email,
    required this.role,
    required this.plainToken
  });

  factory LoginResponse.fromJson(Map<String, dynamic> data) {
    return LoginResponse(
      id: data['id'],
      firstName: data['firstName'],
      lastName: data['lastName'],
      email: data['email'],
      role: UserRoleMapper.fromJson(data['role']),
      plainToken: data['plainToken'],
    );
  }
}
