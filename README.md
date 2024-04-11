# GilGang Group Project

- Startpage: http://localhost:8080/web/welcome
- Profilepage: http://localhost:8080/web/myprofile
- Messagepage: http://localhost:8080/web/messages

### Instructions:
- Clone project
- Go to developer settings on Github https://github.com/settings/developers
- Add a new OAuth app with:
  - Homepage URL: http://localhost:8080
  - Authorization callback URL: http://localhost:8080/login/oauth2/code/github
- Add a new Client secret and copy the code.
- In cloned project add to the Run-configuration these Environment Variables:
  - CLIENT_SECRET={Your copied Clientsecret id};
  - CLIENT_ID={Your copied Client Id from OAuth app}
- DONE, now run the application.
