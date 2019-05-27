export default function reducer(state = { currentUser: {} }, action) {
  switch (action.type) {
    case "USER_LOGGED":
      console.log("reducer", action.payload);
      return onUserLogged(state, action.payload);
    case "USER_LOGGED_OUT":
      return onUserLoggedOut(state, action.payload);
    default:
      return state;
  }
}

function onUserLogged(state, userData) {
  return userData;
}

function onUserLoggedOut(state, userData) {
  return state;
}
