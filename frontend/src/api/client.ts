import { Axios, AxiosRequestConfig, mergeConfig } from "axios";
// @ts-expect-error - default config is not included in ts typings.
// But it's required to create a new instance of axios.
import defaultConfig from "axios/unsafe/defaults";

const baseURL = new URL("/api/v1", import.meta.env.VITE_BACKEND_URL);

class ApiClient extends Axios {
  #interceptorIDs: Record<string, number> = {};
  #refreshToken?: string | null;

  constructor(config: AxiosRequestConfig = {}) {
    super(mergeConfig(defaultConfig, config));

    this.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response && error.response.status === 401) {
          // Implement logic to refresh access token here
          // Handle token invalidation, e.g., redirect to login
          console.error("Unauthorized access - possibly invalid token");
        }

        return Promise.reject(error);
      },
    );
  }

  setTokens(accessToken: string, refreshToken: string) {
    this.#refreshToken = refreshToken;

    this.#interceptorIDs["auth"] = this.interceptors.request.use(
      (cfg) => {
        cfg.headers.Authorization = `Bearer ${accessToken}`;

        return cfg;
      },
      (cfg) => cfg,
    );
  }

  clearTokens() {
    this.#refreshToken = null;

    const interceptorId = this.#interceptorIDs["auth"];

    if (interceptorId !== undefined) {
      this.interceptors.request.eject(interceptorId);
      delete this.#interceptorIDs["auth"];
    }
  }
}

const apiClient = new ApiClient({
  baseURL: baseURL.toString(),
});

export default apiClient;
