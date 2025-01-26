import { Link } from "react-router";

const Home = () => {
  return (
    <div className="flex h-full flex-col items-center justify-center gap-16">
      <h1 className="text-6xl font-bold underline">Home Page!</h1>
      <button className="btn btn-lg btn-primary">
        I'm a button. Click me!
      </button>
      <Link to="about" className="link link-info">
        About the project
      </Link>
    </div>
  );
};

export default Home;
