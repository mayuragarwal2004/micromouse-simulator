import React, { useEffect } from "react";
// import { Motion, spring } from "react-motion";

const ContextMenu = ({
  contextMenu: { showMenu, xPos, yPos, xBlock, yBlock },
  setcontextMenu,
  setstartArray,
  setendArray,
}) => {
  useEffect(() => {
    document.addEventListener("click", (event) => {
      setcontextMenu((prev) => ({ ...prev, showMenu: false }));
    });

    return () => {
      document.removeEventListener("click", (event) => {
        setcontextMenu((prev) => ({ ...prev, showMenu: false }));
      });
    };
  }, []);

  if (showMenu)
    return (
      <>
        {showMenu ? (
          <div
            className="context-menu"
            id="contextMenu"
            style={{
              top: yPos,
              left: xPos,
            }}
          >
            <ul
              className="menu"
              style={{
                top: yPos,
                left: xPos,
              }}
            >
              <li onClick={() => setstartArray([xBlock, yBlock])}>Set Start</li>
              <li onClick={() => setendArray([xBlock, yBlock])}>Set End</li>
            </ul>
          </div>
        ) : (
          <></>
        )}
      </>
    );
  else return null;
};

export default ContextMenu;
