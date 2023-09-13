import React, { useRef, useEffect, useState } from "react";
// import mazeArray from "./mazeArray51.node";
// import {
//   verticalArray,
//   horizontalArray,
//   startArray,
//   endArray,
// } from "./SolveMazeComponents/mazearr";

const Canvas = ({
  width,
  height,
  isRun,
  setisRun,
  path,
  verticalArray,
  horizontalArray,
  startArray,
  endArray,
}) => {
  const canvasRef = useRef(null);
  const mouseRef = useRef(null);
  const mouseFrontRef = useRef(null);
  const mouseBackRef = useRef(null);
  const mouseRightRef = useRef(null);
  const mouseLeftRef = useRef(null);
  const [runStatus, setrunStatus] = useState({ msg: "", run: false });
  const [lastPosition, setlastPosition] = useState(startArray);
  const [rotation, setrotation] = useState(1);

  let intersectionWallSize = 10;
  let wallSize = 10;
  let blockSize = 50;
  let fullBlockSize = blockSize + wallSize;
  let dim = verticalArray.length;

  // const [path, setpath] = useState({
  //   test: [
  //     { position: [1, 0], move: "forward", status: "TRUE", walls: "1010" },
  //     { position: [1, 0], move: "right", status: "TRUE", walls: "1010" },
  //     { position: [1, 0], move: "left", status: "TRUE", walls: "1010" },
  //     { position: [2, 0], move: "forward", status: "TRUE", walls: "1100" },
  //     { position: [2, 0], move: "forward", status: "TRUE", walls: "1100" },
  //   ],
  //   final: [
  //     { position: [1, 0], move: "forward", status: "TRUE", walls: "1010" },
  //     { position: [2, 0], move: "forward", status: "TRUE", walls: "1100" },
  //     { position: [2, 1], move: "forward", status: "TRUE", walls: "1100" },
  //     { position: [2, 1], move: "right", status: "TRUE", walls: "1100" },
  //   ],
  // });

  useEffect(() => {
    // Load the images when the page mounts. Because we're using a ref,
    // they don't need to be in the dependency array or trigger a re-render

    const mouseFront = new Image();
    mouseFront.src = "ratImages/rat-front.webp";
    mouseFront.onload = () => {
      mouseFrontRef.current = mouseFront;
      draw();
    };

    const mouseBack = new Image();
    mouseBack.src = "ratImages/rat-back.webp";
    mouseBack.onload = () => {
      mouseBackRef.current = mouseBack;
      draw();
    };

    const mouseRight = new Image();
    mouseRight.src = "ratImages/rat-right.webp";
    mouseRight.onload = () => {
      mouseRightRef.current = mouseRight;
      draw();
    };

    const mouseLeft = new Image();
    mouseLeft.src = "ratImages/rat-left.webp";
    mouseLeft.onload = () => {
      mouseLeftRef.current = mouseLeft;
      draw();
    };
  }, []);

  const draw = () => {
    console.log(verticalArray);
    console.log(horizontalArray);

    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height);

    const mouse = [
      mouseRightRef.current,
      mouseBackRef.current,
      mouseLeftRef.current,
      mouseFrontRef.current,
    ];
    if (!context || !mouse) {
      return;
    }

    for (let i = 0; i < dim + 1; i++) {
      for (let j = 0; j < dim + 1; j++) {
        // intersection wall
        context.beginPath();
        context.rect(
          j * fullBlockSize,
          i * fullBlockSize,
          intersectionWallSize,
          intersectionWallSize
        );
        context.fillStyle =
          (j < dim && horizontalArray[i][j]) ||
          (i < dim && verticalArray[i][j]) ||
          (j > 0 &&
            i > 0 &&
            (verticalArray[i - 1][j] || horizontalArray[i][j - 1]))
            ? "black "
            : "white ";
        context.fill();
        context.closePath();

        // horizontal wall
        if (j < dim) {
          context.beginPath();
          context.rect(
            j * fullBlockSize + intersectionWallSize,
            i * fullBlockSize,
            blockSize,
            wallSize
          );
          context.fillStyle = horizontalArray[i][j] ? "black " : "white ";
          context.fill();
          context.closePath();
        }

        // vertical wall
        if (i < dim) {
          context.beginPath();
          context.rect(
            j * fullBlockSize,
            i * fullBlockSize + intersectionWallSize,
            wallSize,
            blockSize
          );
          context.fillStyle = verticalArray[i][j] ? "black " : "white ";
          context.fill();
          context.closePath();
        }

        // grid item
        if (i < dim && j < dim) {
          context.beginPath();
          context.rect(
            j * fullBlockSize + intersectionWallSize,
            i * fullBlockSize + intersectionWallSize,
            blockSize,
            blockSize
          );
          context.fillStyle = "white ";
          context.fill();
          context.closePath();
        }
      }
    }

    context.drawImage(
      mouse[rotation - 1],
      startArray[0] * fullBlockSize + intersectionWallSize,
      startArray[1] * fullBlockSize + intersectionWallSize,
      blockSize,
      blockSize
    );
  };

  const animateMouse = (clearMouse, setMouse, move, status, angle) => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");

    const mouse = [
      mouseRightRef.current,
      mouseBackRef.current,
      mouseLeftRef.current,
      mouseFrontRef.current,
    ];
    if (!context || !mouse[0]) {
      return;
    }

    context.clearRect(
      clearMouse[0] * fullBlockSize + intersectionWallSize,
      clearMouse[1] * fullBlockSize + intersectionWallSize,
      blockSize,
      blockSize
    );

    // context.save();
    // context.translate(setMouse[1], setMouse[0]);
    // context.rotate(Math.PI * angle);
    // // context.translate(-x, -y);
    // context.drawImage(mouse, blockSize / 2, blockSize / 2, blockSize, blockSize);
    // context.restore();

    // console.log(angle-1);

    context.drawImage(
      mouse[angle - 1],
      setMouse[0] * fullBlockSize + intersectionWallSize,
      setMouse[1] * fullBlockSize + intersectionWallSize,
      blockSize,
      blockSize
    );
    // else if (move === "right") {
    //   context.save();
    //   context.translate(
    //     setMouse[1] * fullBlockSize + intersectionWallSize,
    //     setMouse[0] * fullBlockSize + intersectionWallSize
    //   );
    //   context.rotate(Math.PI * 90);
    //   // context.translate(-x, -y);
    //   context.drawImage(mouse, 0, 0, blockSize, blockSize);
    //   context.restore();
    // } else if (move === "left") {
    // }
  };

  function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }

  const runMouse = async () => {
    setrunStatus({ msg: "Running Test", run: true });
    console.log(lastPosition);
    let trueLastPosition = lastPosition;
    setrotation(1);
    let trueRotation = 1;

    animateMouse(trueLastPosition, startArray, "forward", "TRUE", trueRotation);
    trueLastPosition = startArray;
    setlastPosition(startArray);
    await sleep(1000);

    path.test.map(async (e, i) => {
      setTimeout(() => {
        if (e.move === "right") {
          setrotation((prev) => (prev + 1 === 5 ? 1 : prev + 1));
          trueRotation = trueRotation + 1 === 5 ? 1 : trueRotation + 1;
        } else if (e.move === "left") {
          setrotation((prev) => (prev - 1 === 0 ? 4 : prev - 1));
          trueRotation = trueRotation - 1 === 0 ? 4 : trueRotation - 1;
        }
        animateMouse(
          trueLastPosition,
          e.position,
          e.move,
          e.status,
          trueRotation
        );
        trueLastPosition = e.position;
        setlastPosition(e.position);
      }, 1000 * i);
    });

    await sleep(1000 * (path.test.length + 2));
    setrunStatus({ msg: "Running Final", run: true });
    setrotation(1);
    trueRotation = 1;
    animateMouse(trueLastPosition, startArray, "forward", "TRUE", trueRotation);
    trueLastPosition = startArray;
    setlastPosition(startArray);
    await sleep(1000);

    path.final.map(async (e, i) => {
      setTimeout(() => {
        if (e.move === "right") {
          setrotation((prev) => (prev + 1 === 5 ? 1 : prev + 1));
          trueRotation = trueRotation + 1 === 5 ? 1 : trueRotation + 1;
        } else if (e.move === "left") {
          setrotation((prev) => (prev - 1 === 0 ? 4 : prev - 1));
          trueRotation = trueRotation - 1 === 0 ? 4 : trueRotation - 1;
        }
        animateMouse(
          trueLastPosition,
          e.position,
          e.move,
          e.status,
          trueRotation
        );
        trueLastPosition = e.position;
        setlastPosition(e.position);
      }, 1000 * i);
    });

    await sleep(1000 * (path.final.length + 2));

    setrunStatus({ msg: "", run: false });

    setisRun(false);
  };

  useEffect(() => {
    if (isRun) {
      runMouse();
    }
  }, [isRun]);

  return (
    <>
      <canvas ref={canvasRef} width={width} height={height} />
      <button onClick={runMouse}>Run Mouse</button>
      {runStatus.run && <div>Run Status: {runStatus.msg}</div>}
    </>
  );
};

export default Canvas;
// export {animateMouse};
