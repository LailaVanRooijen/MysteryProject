import axios from "axios";

const baseURL = new URL("/api/v1", import.meta.env.VITE_BACKEND_URL);
const interceptorIDs: Record<string, number> = {};
const apiClient = axios.create({
  baseURL: baseURL.toString(),
});

function setTokens(accessToken: string, refreshToken: string) {
  interceptorIDs["request-auth"] = apiClient.interceptors.request.use(
    (cfg) => {
      cfg.headers.Authorization = `Bearer ${accessToken}`;

      return cfg;
    },
    (cfg) => cfg,
  );

  interceptorIDs["response-auth"] = apiClient.interceptors.response.use(
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

function clearTokens() {
  const keys = ["request-auth", "response-auth"];

  keys.forEach((key) => {
    const interceptorId = interceptorIDs[key];

    if (interceptorId !== undefined) {
      apiClient.interceptors.request.eject(interceptorId);
      delete interceptorIDs[key];
    }
  });
}

export default apiClient;
export { setTokens, clearTokens };
