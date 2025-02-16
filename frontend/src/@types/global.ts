import AppStore from "@app/stores/AppStore";

declare global {
  interface Window {
    appStore: AppStore;
  }
}
