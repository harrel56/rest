import React from 'react';
import {render} from 'react-dom';
import {Route} from 'react-router'
import {BrowserRouter, Switch} from 'react-router-dom'
import Root from './Root';
import Events from './Events';
import Users from './Users';
import NotFound from './NotFound';

class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Root>
                    <Switch>
                        <Route exact path='/Users' component={Users} />
                        <Route exact path='/Events' component={Events} />
                        <Route component={NotFound} />
                    </Switch>
                </Root>
            </BrowserRouter>
        )
    }
}

render(<App/>, document.getElementById('main'));