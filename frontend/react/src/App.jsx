import UserProfile from "./UserProfile.jsx";
import { useState} from "react";
import { useEffect} from "react";
import {Button, Spinner, Text, Wrap, WrapItem} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/Sidebar.jsx";
import {getCustomers} from "./services/client.js";
import CardWithImage from "./components/Card.jsx";
import CreateCustomerDrawer from "./components/CreateCustomerDrawer.jsx";
import {errorNotification} from "./services/notification.js";

const users = [
    {
        name: "Jamila",
        age: 22,
        gender: "FEMALE"
    },
    {
        name: "Ana",
        age: 45,
        gender: "FEMALE"
    },
    {
        name: "Alex",
        age: 18,
        gender: "MALE"
    },
    {
        name: "Bilal",
        age: 27,
        gender: "MALE"
    },
    {
        name: "Bob",
        age: 27,
        gender: "MALE"
    }
]

const UserProfiles = () => (
        <div>
            {users.map((user, index) => {
                return <UserProfile
                    key={index}
                    name={user.name}
                    age={user.age}
                    gender={user.gender}
                    imageNumber={index}
                />
            })}
        </div>
    )

function App() {

    const [customers, setCustomers] = useState([])
    const [loading, setLoading] = useState(false)
    const [err, setError] = useState("")

    const fetchCustomers = () => {
        setLoading(true)
        getCustomers().then(res => {
            setCustomers(res.data)
        }).catch(err => {
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
            setLoading(false)
        })
    }
    useEffect(() => {
        fetchCustomers();
    }, [])

    if(loading) {
        return (
            <SidebarWithHeader>
            <Spinner
                thickness='4px'
                speed='0.65s'
                emptyColor='gray.200'
                color='blue.500'
                size='xl'
            />
        </SidebarWithHeader>
        )
    }

    if (err) {
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>Oops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if(customers.length <=0) {
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>No customers available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <CreateCustomerDrawer
                fetchCustomers={fetchCustomers}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer, index) => (
                // <p key={index}>{customer.name}</p>
                <WrapItem key={index}>
                    <CardWithImage
                        {...customer}
                        imageNumber = {index}
                        fetchCustomers={fetchCustomers}
                    />
                </WrapItem>
            ))}
            </Wrap>
        </SidebarWithHeader>
    )

    // const [counter, setCounter] = useState(0);
    // const [isLoading, setIsLoading] = useState(false);
    //
    // useEffect(() => {
    //     setIsLoading(true)
    //     setTimeout(() => {
    //         setIsLoading(false);
    //     }, 4000)
    //     return () => {
    //         console.log("cleanup")
    //     }
    // }, [])
    //
    // if(isLoading) {
    //     return "Loading...";
    // }
    //
    // return (
    //     <div>
    //         <button onClick={() => setCounter(prevCounter => prevCounter + 2)}>
    //             Increment counter
    //         </button>
    //         <h1>{counter}</h1>
    //         <UserProfiles/>
    //     </div>
    // )
}

export default App
