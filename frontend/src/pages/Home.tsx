import { Link } from "react-router";

import { setTokens } from "@app/api/client";
import { sayHello } from "@app/api/core";

const Home = () => {
  return (
    <div className="flex h-full flex-col items-center justify-center gap-16">
      <h1 className="text-6xl font-bold underline">Home Page!</h1>
      <button
        className="btn btn-lg btn-primary"
        onClick={async () => {
          // Demo
          await sayHello();
          setTokens("access_token_value", "refresh_token_value");
          await sayHello();
        }}
      >
        I'm a button. Click me!
      </button>
      <Link to="about" className="link link-info">
        About the project
      </Link>
    </div>
  );
};

export default Home;
