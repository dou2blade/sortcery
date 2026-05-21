import 'package:go_router/go_router.dart';
import 'package:sortcery/core/dependencies/dependency_injector.dart';
import 'package:sortcery/core/router/routes/auth_routes.dart';
import 'package:sortcery/features/auth/ui/view_model/auth_view_model.dart';

final AuthViewModel authViewModel = DependencyInjector.authViewModel;

final GoRouter router = GoRouter(
  initialLocation: '/login',
  refreshListenable: authViewModel,
  redirect: (context, state) {
    final isLoggedIn = authViewModel.isLoggedIn;
    final location = state.uri.path;

    final isAuthRoute = location.startsWith('/login') || location == '/register';

    if (!isLoggedIn && !isAuthRoute) {
      return '/login';
    }

    if (isLoggedIn && isAuthRoute) {
      return '/${authViewModel.session!.role.name}';
    }

    return null;
  },
  routes: [
    ...authRoutes
  ],
);
