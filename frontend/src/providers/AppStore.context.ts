import { createContext } from "react";

import AppStore from "@app/stores/AppStore";

const appStore = new AppStore();
const AppStoreContext = createContext(appStore);

// For debugging purpose.
// So it's easier to access store from browser console.
window.appStore = appStore;

export default AppStoreContext;
export { appStore };
