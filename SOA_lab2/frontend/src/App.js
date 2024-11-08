import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'; // Correctly import Router
import CityList from './components/CityList';
import CityForm from './components/CityForm';
import SecondService from './components/SecondService';

function App() {
    return (

        <Router basename="/~s338960/SOA2"> {/* Wrapping the Routes with Router */}
            <div className="App">
                <Routes>
                    <Route path="/" element={<CityList />} />
                    <Route path="/create-city" element={<CityForm />} />
                    <Route path="/edit-city/:id" element={<CityForm />} />
                    <Route path="/second-service" element={<SecondService />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
