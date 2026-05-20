import 'package:sortcery/core/auth/token_storage.dart';
import 'package:sortcery/core/network/api_client.dart';
import 'package:sortcery/features/auth/data/auth_api.dart';
import 'package:sortcery/features/auth/data/auth_repository.dart';
import 'package:sortcery/features/auth/ui/view_model/auth_view_model.dart';

class DependencyInjector {
  static late final TokenStorage tokenStorage;
  static late final ApiClient apiClient;
  static late final AuthApi authApi;
  static late final AuthRepository authRepository;
  static late final AuthViewModel authViewModel;

  static void init() {
    print("INIT START");

    tokenStorage = TokenStorage();
    print("TokenStorage OK");

    apiClient = ApiClient(tokenStorage);
    print("ApiClient OK");

    authApi = AuthApi(apiClient);
    print("AuthApi OK");

    authRepository = AuthRepository(authApi, tokenStorage);
    print("AuthRepository OK");

    authViewModel = AuthViewModel(authRepository);
    print("AuthViewModel OK");

    print("INIT DONE");
  }
}
