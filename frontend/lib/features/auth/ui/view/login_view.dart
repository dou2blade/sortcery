import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sortcery/core/dependencies/dependency_injector.dart';
import 'package:sortcery/core/enums/user_role.dart';
import 'package:sortcery/core/network/api_result.dart';
import 'package:sortcery/features/auth/data/dto/login_request.dart';
import 'package:sortcery/features/auth/domain/auth_session.dart';
import 'package:sortcery/features/auth/ui/widget/auth_card.dart';
import 'package:sortcery/features/auth/ui/widget/auth_header.dart';
import 'package:sortcery/features/auth/ui/widget/auth_redirect_text.dart';
import 'package:sortcery/features/auth/ui/widget/auth_staff_roles_dropdown_menu.dart';
import 'package:sortcery/features/auth/ui/widget/auth_submit_button.dart';
import 'package:sortcery/features/auth/ui/widget/auth_text_field.dart';

class LoginView extends StatefulWidget {
  final bool isConsumer;

  const LoginView({ super.key, required this.isConsumer });

  @override
  State<LoginView> createState() => _LoginViewState();
}

class _LoginViewState extends State<LoginView> {
  final emailCtrl = TextEditingController();
  final passCtrl = TextEditingController();

  bool loading = false;
  String? feedback;
  UserRole role = UserRole.consumer;

  void _login() async {
    setState(() { 
      loading = true;
      feedback = null;
    });

    final res = await DependencyInjector.authViewModel.login( 
      LoginRequest(email: emailCtrl.text, password: passCtrl.text),
      role
    );

    if (res is ApiError<AuthSession>) {
      setState(() => feedback = res.message);
    }

    setState(() => loading = false);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          AuthCard(
            child: Column( 
              mainAxisSize: MainAxisSize.min,
              children: [
                AuthHeader(title: "Welcome back", subtitle: "Enter your credentials to continue"),
                AuthTextField(controller: emailCtrl, label: "Email", icon: Icons.email_outlined, errorText: feedback, hideErrorText: true),
                AuthTextField(controller: passCtrl, label: "Password", icon: Icons.password_outlined, obscureText: true, errorText: feedback, hideErrorText: !widget.isConsumer),
                if (!widget.isConsumer) AuthStaffRolesDropdownMenu(
                  value: role, 
                  errorText: feedback,
                  onSelected: (value) => setState(() => role = value)
                ),
                AuthSubmitButton(label: "Login", onPressed: _login, icon: Icons.login_outlined, loading: loading),
                AuthRedirectText(
                  label: "", 
                  actionText: widget.isConsumer ? "Staff Portal" : "Consumer Login", 
                  onTap: () => context.go(widget.isConsumer ? "/login/staff" : "/login")
                ),
              ],
            )
          )
        ]
      )
    );
  }
}
