import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import https from "node:https";

function SecondaryService() {
    const agent = new https.Agent({
        rejectUnauthorized: false
    });

    const defaultCityIds = { id1: '1', id2: '2', id3: '3' }; // Example default city IDs
    const defaultMoveCityId = '1'; // Example default city ID to move

    const [cityIds, setCityIds] = useState(defaultCityIds);
    const [moveCityId, setMoveCityId] = useState(defaultMoveCityId);
    const [totalPopulation, setTotalPopulation] = useState(null);
    const [moveMessage, setMoveMessage] = useState('');

    const getTotalPopulation = useCallback(() => {
        const { id1, id2, id3 } = cityIds;
        if (id1 && id2 && id3) {
            axios
                .get(`https://localhost:22701/genocide/count/${id1}/${id2}/${id3}`, {
                    headers: {
                        'Accept': 'application/xml',
                        'Content-Type': 'application/xml'
                    },
                    httpsAgent: agent
                })
                .then((response) => {
                    setTotalPopulation(parseInt(response.data, 10)); // Assuming the response is a string number
                })
                .catch((error) => {
                    alert('Error fetching total population:' + error.message);
                    console.error('Error fetching total population:', error);
                });
        } else {
            alert('Please enter valid city IDs.');
        }
    }, [cityIds]);

    const movePopulation = () => {
        if (moveCityId) {
            axios
                .post(`https://localhost:22701/genocide/move-to-poorest/${moveCityId}`, {}, {
                    headers: {
                        'Accept': 'application/xml',
                        'Content-Type': 'application/xml'
                    },
                    httpsAgent: agent
                })
                .then(() => {
                    setMoveMessage('The population of the city is successfully moved!');
                })
                .catch((error) => {
                    alert('Error moving population:' + error.message);
                    console.error('Error moving population:', error);
                });
        } else {
            alert('Please enter a valid city ID.');
        }
    };

    return (
        <div>
            <h1>Secondary Service</h1>

            <div className="filter-sort-container">
                <div className="filter-container">
                    <label htmlFor="id1">City ID 1:</label>
                    <input
                        type="number"
                        id="id1"
                        value={cityIds.id1}
                        onChange={(e) => setCityIds({ ...cityIds, id1: e.target.value })}
                        required
                    />
                    <label htmlFor="id2">City ID 2:</label>
                    <input
                        type="number"
                        id="id2"
                        value={cityIds.id2}
                        onChange={(e) => setCityIds({ ...cityIds, id2: e.target.value })}
                        required
                    />
                    <label htmlFor="id3">City ID 3:</label>
                    <input
                        type="number"
                        id="id3"
                        value={cityIds.id3}
                        onChange={(e) => setCityIds({ ...cityIds, id3: e.target.value })}
                        required
                    />
                    <button onClick={getTotalPopulation}>Get Total Population</button>
                </div>
            </div>

            {totalPopulation !== null && (
                <p>Total Population: {totalPopulation}</p>
            )}

            <div className="filter-sort-container">
                <div className="filter-container">
                    <label htmlFor="moveId">City ID to Move Population:</label>
                    <input
                        type="number"
                        id="moveId"
                        value={moveCityId}
                        onChange={(e) => setMoveCityId(e.target.value)}
                        required
                    />
                    <button onClick={movePopulation}>Move to Poorest City</button>
                </div>
            </div>

            {moveMessage && <p>{moveMessage}</p>}

            <Link to="/" className="router-link route">Back to Main Service</Link>
        </div>
    );
}

export default SecondaryService;
