const express = require("express");
const app = express();
const bodyParser = require("body-parser");
var fs = require("fs");
const { exec } = require("child_process");
const port = 5000;

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

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

app.post("/api", async (req, res) => {
  const dim = req.body.dim;
  const verticalArray = req.body.verticalArray;
  const horizontalArray = req.body.horizontalArray;
  const code = req.body.code;
  const uid = "1";
  let output = { errormsg: "", error: false, path: false, output: false };

  // start and end

  fs.writeFile("Java_trash\\Main.java", code, function (err) {
    if (err) throw err;
    console.log("File is created successfully.");
  });

  await sleep(2000);
  // res.json(output)
  // return

  exec(
    "javac -cp Java_trash .\\Java_trash\\Main.java",
    (error, stdout, stderr) => {
      if (error) {
        console.log(`error: ${error.message}`);
        output = { ...output, errormsg: error, error: true };
        return;
      }
      if (stderr) {
        console.log(`stderr: ${stderr}`);
        output = { ...output, errormsg: stderr, error: true };
        return;
      }
      console.log(`Compiled successfully`);
    }
  );
  await sleep(2000);

  if (output.error) {
    res.json(output);
    return;
  }

  let vstr = "";
  let hstr = "";

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

  console.log(uid + " " + hstr + "\n" + vstr);
  const postData = uid + " " + verticalArray.length + " " + hstr + " " + vstr;

  if (!output.error) {
    var mazeManager = net.connect(8090, "localhost");
    mazeManager.write(postData);
    // mazeManager.
    await sleep(2000);
    console.log("Executing the script");
    exec("java -cp .\\Java_trash\\ Main", (error, stdout, stderr) => {
      if (error) {
        console.log(`error: ${error.message}`);
        output = { ...output, errormsg: error.message, error: true };
        return;
      }
      if (stderr) {
        console.log(`stderr: ${stderr}`);
        output = { ...output, errormsg: stderr, error: true };
        return;
      }
      console.log(`stdout: ${stdout}`);
      output = { ...output, output: stdout, error: false };
    });
    mazeManager.on("data", (data) => {
      console.log(data.toString());
      output = {
        ...output,
        path: JSON.parse(data.toString()),
        errormsg: "",
        error: false,
      };
      // res.json(JSON.parse(data.toString()));
    });
    mazeManager.on("error", (data) => {
      console.log(data.toString());
      output = {
        ...output,
        errormsg: "Something went wrong, please contact the developer.",
        error: true,
        path: false,
      };
    });
    mazeManager.end();
  }
  while (!(output.error || (output.path && output.output))) sleep(50);
  res.json(output);
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
