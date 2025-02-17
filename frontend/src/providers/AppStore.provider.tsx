import { FC, PropsWithChildren } from "react";

import AppStoreContext, { appStore } from "./AppStore.context";

const AppStoreProvider: FC<PropsWithChildren> = (props) => {
  return (
    <AppStoreContext.Provider value={appStore}>
      {props.children}
    </AppStoreContext.Provider>
  );
};

export default AppStoreProvider;
