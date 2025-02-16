import { Link } from "react-router";

import { observer } from "mobx-react";

import { setTokens } from "@app/api/client";
import { sayHello } from "@app/api/core";
import useStore from "@app/hooks/useStore";

const Home = () => {
  const store = useStore();

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

      <div className="flex items-center gap-4">
        <button
          className="btn btn-circle"
          onClick={store.decrement.bind(store)}
        >
          -
        </button>
        <h2>{store.counter}</h2>
        <button
          className="btn btn-circle"
          onClick={store.increment.bind(store)}
        >
          +
        </button>
      </div>

      <Link to="about" className="link link-info">
        About the project
      </Link>
    </div>
  );
};

const HomePage = observer(Home);

export default HomePage;
