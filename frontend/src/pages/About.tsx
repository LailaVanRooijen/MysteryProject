import { Link } from "react-router";

const About = () => {
  return (
    <div className="flex h-full flex-col items-center justify-center gap-16">
      <h1 className="text-6xl font-bold underline">About Project Page!</h1>
      <p className="text-2xl">This is a mystery project!</p>
      <Link to="/" className="link link-info">
        Go home
      </Link>
    </div>
  );
};

export default About;
