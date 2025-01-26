import { BrowserRouter, Route, Routes } from "react-router";

import "@app/App.css";
import "@app/index.css";

import About from "@app/pages/About";
import Home from "@app/pages/Home";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" index element={<Home />} />
        <Route path="/about" index element={<About />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
