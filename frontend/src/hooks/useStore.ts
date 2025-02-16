import { useContext } from "react";

import AppStoreContext from "@app/providers/AppStore.context";

export default function useStore() {
  return useContext(AppStoreContext);
}
