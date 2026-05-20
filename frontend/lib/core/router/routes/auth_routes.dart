import 'package:go_router/go_router.dart';
import 'package:sortcery/core/enums/user_role.dart';
import 'package:sortcery/features/auth/ui/view/login_view.dart';

final authRoutes = [
  GoRoute(
    path: '/login', 
    builder: (context, state) => LoginView(targetRole: UserRole.admin)
  )
];
