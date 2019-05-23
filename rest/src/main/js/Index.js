import React from "react";
import { render } from "react-dom";
import { Route } from "react-router";
import { BrowserRouter, Switch } from "react-router-dom";
import { Provider } from "react-redux";
import { createStore } from "redux";
import reducer from "./state/reducers";
import Root from "./Root";
import Events from "./Events";
import Users from "./Users";
import NotFound from "./NotFound";

const store = createStore(reducer);

class App extends React.Component {
  render() {
    return (
      <Provider store={store}>
        <BrowserRouter>
          <Root>
            <Switch>
              <Route exact path="/Users" component={Users} />
              <Route exact path="/Events" component={Events} />
              <Route component={NotFound} />
            </Switch>
          </Root>
        </BrowserRouter>
      </Provider>
    );
  }
}

render(<App />, document.getElementById("main"));
