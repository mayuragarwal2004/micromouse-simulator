import './App.css';
import MazeMaker from './components/MazeMaker';
import SolveMaze from './components/SolveMaze';

import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <>
    {/* <React.StrictMode> */}
      <BrowserRouter>
        <div className="home-container">
          <Routes>
            <Route path="/mazemaker" element={<MazeMaker />} />
            <Route path="/solvemaze" element={<SolveMaze />} />
          </Routes>
        </div>
      </BrowserRouter>
      {/* </React.StrictMode> */}
    </>
  );
}

export default App;
