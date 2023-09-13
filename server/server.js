const express = require("express");
const app = express();
const bodyParser = require("body-parser");
var fs = require("fs");
var mongoose = require("mongoose");
const { exec } = require("child_process");
const port = 5000;

// mongoose.connect("mongodb://localhost:27017/micromousedb", {
//   useNewUrlParser: true,
//   useFindAndModify: false,
//   useUnifiedTopology: true,
// });

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

// var db = mongoose.connection;

// db.on("error", console.error.bind(console, "connection error:"));
// db.once("open", function () {
//   console.log("Connection Successful!");

//   const mazeSchema = {
//     verticalArray: { type: Array },
//     horizontalArray: { type: Array },
//     startArray: { type: Array },
//     endArray: { type: Array },
//   };

//   const MazeModel = mongoose.model("Maze", mazeSchema);

//   const maze = new MazeModel({
//     verticalArray: [1, 1],
//     horizontalArray: [1, 1],
//     startArray: [1, 1],
//     endArray: [1, 1],
//   });

//   maze.save();
// });
var net = require("net");

// app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json());
// var serviceAccount = require(__dirname + "/serviceAccountKey.json");

async function grantRole(currentUserUID, phno, role) {
  let user1; // user1 - Who added the other user as the role
  let user2; // user2 - Who is being assigned the role
  try {
    user1 = await admin.auth().getUser(currentUserUID);
  } catch (err) {
    return { ok: false, info: err.code };
  }

  try {
    user2 = await admin.auth().getUserByPhoneNumber(phno);
  } catch (err) {
    return { ok: false, info: err.code };
  }
  console.log(user1.customClaims);
  if (JSON.stringify(user1.customClaims) != "{}") {
    if (
      Boolean(
        roles[user1.customClaims.role].find(({ value }) => value === role)
      )
    ) {
      try {
        await admin
          .auth()
          .setCustomUserClaims(user2.uid, { role: role })
          .then(() => {
            const ref = db.ref("roleHistory");
            ref.push({ madeBy: user1.uid, madeTo: user2.uid, made: role });
          });
        return { ok: true, info: "Role Assigned Successfully" };
      } catch (err) {
        return { ok: false, info: err.code };
      }
    } else {
      return {
        ok: false,
        info: "Your role is not allowed to assign anyone " + role,
      };
    }
  } else {
    return { ok: false, info: "You are not elligible to assign anyone role" };
  }
  return { ok: true, info: null };
}

async function runCode() {}

app.post("/submitMaze", async (req, res) => {
  const dim = req.body.dim;
  const verticalArray = req.body.verticalArray;
  const horizontalArray = req.body.horizontalArray;
  const startArray = req.body.startArray;
  const endArray = req.body.endArray;

  const maze = new MazeModel({
    verticalArray,
    horizontalArray,
    startArray,
    endArray,
  });

  maze.save();
});

app.post("/api", async (req, res) => {
  const dim = req.body.dim;
  const verticalArray = req.body.verticalArray;
  const horizontalArray = req.body.horizontalArray;
  const startArray = req.body.startArray;
  const endArray = req.body.endArray;
  const code = req.body.code;
  const uid = "1";
  let output = { errormsg: "", error: false, path: false, output: false };

  // start and end

  fs.writeFile("Java_trash\\Main.java", code, function (err) {
    if (err) throw err;
    console.log("File is created successfully.");
  });

  exec(
    "javac -cp Java_trash .\\Java_trash\\Main.java",
    (error, stdout, stderr) => {
      if (error) {
        output = { ...output, errormsg: error, error: true };
        console.log(`error: ${error.message}`);
        return;
      }
      if (stderr) {
        output = { ...output, errormsg: stderr, error: true };
        console.log(`stderr: ${stderr}`);
        return;
      }
      console.log(`Compiled successfully`);
      let vstr = "";
      let hstr = "";
      let sstr = "";
      let estr = "";

      for (let i = 0; i < verticalArray.length; i += 1) {
        for (let j = 0; j < verticalArray[i].length; j += 1) {
          vstr += verticalArray[i][j] ? 1 : 0;
        }
        vstr += ",";
      }

      for (let i = 0; i < horizontalArray.length; i += 1) {
        for (let j = 0; j < horizontalArray[i].length; j += 1) {
          hstr += horizontalArray[i][j] ? 1 : 0;
        }
        hstr += ",";
      }

      for (let i = 0; i < startArray.length; i++) {
        sstr += startArray[i];
      }

      for (let i = 0; i < endArray.length; i++) {
        estr += endArray[i];
      }

      console.log(uid + " " + hstr + "\n" + vstr);
      const postData = [uid, verticalArray.length, hstr, vstr, sstr, estr].join(
        " "
      );

      var mazeManager = net.connect(8090, "localhost");
      mazeManager.write(postData);
      // mazeManager.
      sleep(2000);
      exec("java -cp .\\Java_trash\\ Main", (error, stdout, stderr) => {
        if (error) {
          output = { ...output, errormsg: error.message, error: true };
          console.log(`error: ${error.message}`);
          return;
        }
        if (stderr) {
          output = { ...output, errormsg: stderr, error: true };
          console.log(`stderr: ${stderr}`);
          return;
        }

        output = { ...output, output: stdout, error: false };
        console.log(`stdout: ${stdout}`);
        console.log("output.toString()");
        console.log(output);
        mazeManager.on("data", (data) => {
          // console.log(data.toString());
          // res.json(JSON.parse(data.toString()));
          console.log(data.toString());
          output = {
            ...output,
            path: JSON.parse(data.toString()),
            errormsg: "",
            error: false,
          };
          console.log("output.toString()");
          console.log(output.toString());
          res.json(output);
        });
      });
      mazeManager.end();
    }
  );

  if (output.error) {
    res.json(output);
    return;
  }

  // const requestOptions = {
  //   method: "POST",
  //   headers: { "Content-Type": "text/plain" },
  //   body: postData,
  // };
  // fetch("localhost:8090", requestOptions)
  //   .then((data) => console.log(data));

  // console.log(hstr);

  // run java file
  // exec("ping www.google.com", (error, stdout, stderr) => {
  //   if (error) {
  //     console.log(`error: ${error.message}`);
  //     return;
  //   }
  //   if (stderr) {
  //     console.log(`stderr: ${stderr}`);
  //     return;
  //   }
  //   console.log(`stdout: ${stdout}`);
  // });

  // res.json({ success: true });
  // console.log(data);
});

app.listen(port, () =>
  console.log(`MicroMouse Server is listening on port ${port}!`)
);
