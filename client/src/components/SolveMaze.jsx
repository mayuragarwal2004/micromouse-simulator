import React, { useState } from "react";
import Canvas from "./SolveMazeComponents/Canvas";
import {
  verticalArray,
  horizontalArray,
  startArray,
  endArray,
} from "./SolveMazeComponents/mazearr";
import "./solvemaze.css";

const SolveMaze = () => {
  const [code, setCode] = useState("");
  const [isRun, setisRun] = useState(false);
  const [response, setresponse] = useState({
    errormsg: "",
    error: false,
    path: false,
    output: false,
  });

  const [path, setpath] = useState({
    test: [],
    final: [],
  });

  const handleCodeChange = (event) => {
    let v = event.target.value;
    setCode(v);
  };

  function handleVerifyCode() {
    // if (num.valid && roleSelect && currentUser) {
    const postData = {
      dim: verticalArray.length,
      verticalArray,
      horizontalArray,
      startArray,
      endArray,
      code,
    };
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(postData),
    };
    fetch("/api", requestOptions)
      .then((response) => response.json())
      .then((data) => {
        console.log("got response");
        setresponse(data);
        if (!data.error) {
          setpath(data.path);
          console.log(data);
        }
      });
    // } else {
    //   console.log("invalid");
    // }
  }

  return (
    <>
      <div className="solve-maze-main">
        <div className="canvas-parent">
          <Canvas
            width="300px"
            height="300px"
            path={path}
            isRun={isRun}
            setisRun={setisRun}
            verticalArray={verticalArray}
            horizontalArray={horizontalArray}
            startArray={startArray}
            endArray={endArray}
          />
        </div>
        <div>
          <textarea
            value={code}
            onChange={handleCodeChange}
            className="code-area"
          />
          <button onClick={handleVerifyCode}>Submit Code</button>
          {response.error && <div>Error: {response.errormsg}</div>}
          {response.output && <div>Code Output: {response.output}</div>}
        </div>
      </div>
    </>
  );
};

export default SolveMaze;
