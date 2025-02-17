import { BrowserRouter, Route, Routes } from "react-router";

import "@app/App.css";
import "@app/index.css";

import About from "@app/pages/About";
import Home from "@app/pages/Home";
import AppStoreProvider from "@app/providers/AppStore.provider";

function App() {
  return (
    <AppStoreProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" index element={<Home />} />
          <Route path="/about" index element={<About />} />
        </Routes>
      </BrowserRouter>
    </AppStoreProvider>
  );
}

export default App;
