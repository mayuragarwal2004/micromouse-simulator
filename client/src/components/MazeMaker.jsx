import React, { useState, useEffect } from "react";
import ContextMenu from "./ContextMenu";
import "./mazemaker.css";
import ratright from "./SolveMazeComponents/rat images/rat-right.webp";
import cheese from "./SolveMazeComponents/rat images/cheese.webp";

const MazeMaker = () => {
  const [dim, setdim] = useState(4);
  const [tempdim, settempdim] = useState(4);
  const [wallSize, setwallSize] = useState(10);
  const [gridlinesize, setgridlinesize] = useState(4);
  const [blockSize, setblockSize] = useState(50);
  const [gridlines, setgridlines] = useState(true);
  const [horizontalArray, sethorizontalArray] = useState([]);
  const [verticalArray, setverticalArray] = useState([]);
  const [startArray, setstartArray] = useState([1, 1]);
  const [endArray, setendArray] = useState([dim, dim]);
  const [contextMenu, setcontextMenu] = useState({
    xPos: "0px",
    yPos: "0px:",
    xBlock: 0,
    yBlock: 0,
    showMenu: false,
  });

  const arrayRange = (start, stop, step) => {
    var arr = [];
    for (let i = 0; i < stop; i++) {
      const element = Array.from(
        { length: (stop - start) / step + 1 },
        (value, index) => start + index * step
      );
      arr.push(element);
    }
    return arr;
  };

  const setDefaultWalls = () => {
    setdim(tempdim);
    sethorizontalArray(() => {
      var arr = [];
      for (let i = 0; i < tempdim + 1; i++) {
        var arr2 = [];
        for (let j = 0; j < tempdim; j++) {
          if (i === 0 || i === tempdim) arr2.push(true);
          else arr2.push(false);
        }
        arr.push(arr2);
      }
      return arr;
    });

    setverticalArray(() => {
      var arr = [];
      for (let i = 0; i < tempdim; i++) {
        var arr2 = [];
        for (let j = 0; j < tempdim + 1; j++) {
          if (j === 0 || j === tempdim) arr2.push(true);
          else arr2.push(false);
        }
        arr.push(arr2);
      }
      return arr;
    });

    setstartArray([0, 0]);
    setendArray([tempdim-1, tempdim-1]);
  };

  const horizontalWallClick = (i, j) => {
    if (j === dim) return;
    console.log("clicked ".concat(i, "x", j, " = ", horizontalArray[i][j]));
    sethorizontalArray((prev) => {
      var arr1 = [];
      prev.forEach((element, indexi) => {
        var arr2 = [];
        element.forEach((elementj, indexj) => {
          if (indexi === i && indexj === j) arr2.push(!elementj);
          else arr2.push(elementj);
        });
        arr1.push(arr2);
      });
      return arr1;
    });
  };

  const verticalWallClick = (i, j) => {
    if (i === dim) return;
    console.log("clicked ".concat(i, "x", j, " = ", verticalArray[i][j]));
    setverticalArray((prev) => {
      var arr1 = [];
      prev.forEach((element, indexi) => {
        var arr2 = [];
        element.forEach((elementj, indexj) => {
          if (indexi === i && indexj === j) arr2.push(!elementj);
          else arr2.push(elementj);
        });
        arr1.push(arr2);
      });
      return arr1;
    });
  };

  const handleWallSizeChange = (s) => {
    if (s === "-") {
      setwallSize((prev) => prev - 2);
      setgridlinesize((prev) => prev - 2);
    } else if (s === "+") {
      setwallSize((prev) => prev + 2);
      setgridlinesize((prev) => prev + 2);
    }
  };

  const handleGridlineSizeChange = (s) => {
    if (s === "-") {
      setgridlinesize((prev) => prev - 2);
    } else if (s === "+") {
      setgridlinesize((prev) => prev + 2);
    }
  };

  const handleBlockSizeChange = (s) => {
    if (s === "-") setblockSize((prev) => prev - 5);
    else if (s === "+") setblockSize((prev) => prev + 5);
  };

  const handleDimChange = (e) => {
    const v = e.target.value;
    settempdim(parseInt(v));
  };

  useEffect(() => {
    setDefaultWalls();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    document.documentElement.style.setProperty(
      "--gridline-width",
      (gridlinesize / 2).toString().concat("px")
    );
    document.documentElement.style.setProperty(
      "--wall-size",
      wallSize.toString().concat("px")
    );
    document.documentElement.style.setProperty(
      "--intersection-gridline-width",
      (wallSize - gridlinesize).toString().concat("px")
    );
  }, [gridlinesize, wallSize]);

  const submitMaze = () => {};

  useEffect(() => {
    console.log("start array");
    console.log(startArray);

    console.log("end array");
    console.log(endArray);
  }, [startArray, endArray]);

  return (
    <>
      <div className="main-mazemaker-body">
        <div className="mazemaker-display maze-parent">
          <div
            className="maze-main"
            style={{
              gridTemplateColumns: wallSize
                .toString()
                .concat("px ", blockSize, "px ")
                .repeat(dim)
                .concat(wallSize, "px ", "0px "),
              gridTemplateRows: wallSize
                .toString()
                .concat("px ", blockSize, "px ")
                .repeat(dim)
                .concat(wallSize, "px ", "0px "),
            }}
          >
            {horizontalArray.length !== 0 &&
              verticalArray.length !== 0 &&
              arrayRange(1, dim + 1, 1).map((n, i) => (
                <>
                  {n.map((m, j) => (
                    <>
                      <div
                        key={i.toString() + j.toString}
                        className={"intersection-wall ".concat(
                          gridlines ? "wall-show " : "wall-hidden ",
                          (j < dim && horizontalArray[i][j]) ||
                            (i < dim && verticalArray[i][j]) ||
                            (j > 0 &&
                              i > 0 &&
                              (verticalArray[i - 1][j] ||
                                horizontalArray[i][j - 1]))
                            ? "fill "
                            : "unfill "
                        )}
                        onClick={() =>
                          console.log("intersection clicked: " + i + "x" + j)
                        }
                      >
                        <div className="intersection-wall-vertical " />
                        <div className="intersection-wall-horizontal " />
                      </div>
                      <div
                        onClick={() => horizontalWallClick(i, j)}
                        className={"horizontal-wall ".concat(
                          gridlines ? "wall-show " : "wall-hidden ",
                          j < dim
                            ? horizontalArray[i][j]
                              ? "fill "
                              : "unfill "
                            : "rightextreme-wall "
                        )}
                      ></div>
                    </>
                  ))}
                  {n.map((m, j) => (
                    <>
                      <div
                        key={i.toString() + j.toString}
                        onClick={() => verticalWallClick(i, j)}
                        className={"vertical-wall ".concat(
                          gridlines ? "wall-show " : "wall-hidden ",
                          i < dim
                            ? verticalArray[i][j]
                              ? "fill "
                              : "unfill "
                            : "bottomextreme-wall "
                        )}
                      />
                      <div
                        className={"grid-item ".concat(
                          j < dim
                            ? horizontalArray[i][j]
                              ? "top "
                              : ""
                            : "rightextreme ",
                          i < dim
                            ? verticalArray[i][j]
                              ? "left "
                              : ""
                            : "bottomextreme "
                        )}
                        onContextMenu={(event) => {
                          event.preventDefault();
                          setcontextMenu((prev) => ({
                            ...prev,
                            xPos: event.pageX + "px",
                            yPos: event.pageY + "px",
                            xBlock: i,
                            yBlock: j,
                            showMenu: true,
                          }));
                          // const xPos = event.pageX + "px";
                          // const yPos = event.pageY + "px";
                          console.log({i,j})
                        }}
                      >
                        {startArray[0] === i && startArray[1] === j && (
                          <img src={ratright} alt="start" height={blockSize} />
                        )}
                        {endArray[0] === i && endArray[1] === j && (
                          <img src={cheese} alt="start" height={blockSize} />
                        )}
                      </div>
                    </>
                  ))}
                </>
              ))}
          </div>
        </div>
        <div className="mazemaker-settings">
          <div className="mazemaker-settings-dimensions">
            <label htmlFor="mazemaker-settings-dimensions-input">
              Dimensions:{" "}
            </label>
            <input
              className="mazemaker-settings-dimensions-input"
              id="mazemaker-settings-dimensions-input"
              type="number"
              value={tempdim}
              onChange={handleDimChange}
            />{" "}
            <button onClick={setDefaultWalls}>Go</button>
          </div>
          <div className="mazemaker-settings-wallsize">
            Wall Size:{" "}
            <button onClick={() => handleWallSizeChange("-")}>-</button>{" "}
            {wallSize}{" "}
            <button onClick={() => handleWallSizeChange("+")}>+</button>
          </div>
          <div className="mazemaker-settings-gridlinesize">
            Gridline Size:{" "}
            <button onClick={() => handleGridlineSizeChange("-")}>-</button>{" "}
            {gridlinesize}{" "}
            <button onClick={() => handleGridlineSizeChange("+")}>+</button>
          </div>
          <div className="mazemaker-settings-blocksize">
            Block Size:{" "}
            <button onClick={() => handleBlockSizeChange("-")}>-</button>{" "}
            {blockSize}{" "}
            <button onClick={() => handleBlockSizeChange("+")}>+</button>
          </div>
          <div className="mazemaker-settings-resetsize">
            <button onClick={setDefaultWalls}>Reset Maze</button>{" "}
          </div>
          <div className="mazemaker-settings-resetsize">
            <label htmlFor="gridlines-input">Gridlines:</label>
            <input
              type="checkbox"
              id="gridlines-input"
              checked={gridlines}
              onChange={() => setgridlines(!gridlines)}
            />
          </div>
          <div className="mazemaker-settings-submit">
            <button onClick={submitMaze}>Submit Maze</button>{" "}
          </div>
        </div>
      </div>
      <ContextMenu
        contextMenu={contextMenu}
        setstartArray={setstartArray}
        setendArray={setendArray}
        setcontextMenu={setcontextMenu}
      />
    </>
  );
};

export default MazeMaker;
