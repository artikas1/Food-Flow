import React from "react";
import { Box, Button, TextField, Select, MenuItem, InputLabel, FormControl } from "@mui/material";
import { Link } from "react-router-dom";
import { UserDto } from "../../dtos/UserDto.tsx";

type RegisterFormProps = {
    title: string;
    userDto: UserDto;
    onSubmit: (e: React.FormEvent) => void;
    onChange: (field: string, value: string) => void;
};

export const RegisterForm: React.FC<RegisterFormProps> = ({
                                                              title,
                                                              onSubmit,
                                                              onChange,
                                                              userDto
                                                          }) => {
    return (
        <div className="flex justify-center items-center h-screen">
            <div className="absolute inset-0 bg-[#AEC761] opacity-20"></div>
            <Box className="relative p-6 bg-white rounded shadow w-96">
                <img src="/ff-logo.png" alt="Logo" className="absolute top-5 right-2 w-12 h-12" />
                <h3 className="text-4xl mb-7 text-left">{title}</h3>
                <form onSubmit={onSubmit}>
                    <TextField
                        label="First Name"
                        type="text"
                        value={userDto.name}
                        onChange={(e) => onChange("name", e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                    <TextField
                        label="Last Name"
                        type="text"
                        value={userDto.surname}
                        onChange={(e) => onChange("surname", e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                    <TextField
                        label="Email"
                        type="email"
                        value={userDto.email}
                        onChange={(e) => onChange("email", e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                    <TextField
                        label="Password"
                        type="password"
                        value={userDto.password}
                        onChange={(e) => onChange("password", e.target.value)}
                        fullWidth
                        margin="dense"
                    />
                    <TextField
                        label="Birthdate"
                        type="date"
                        value={userDto.birthDate}
                        onChange={(e) => onChange("birthDate", e.target.value)}
                        fullWidth
                        margin="dense"
                        InputLabelProps={{ shrink: true }}
                    />
                    <FormControl fullWidth margin="dense">
                        <InputLabel id="gender-label">Gender</InputLabel>
                        <Select
                            labelId="gender-label"
                            value={userDto.gender}
                            onChange={(e) => onChange("gender", e.target.value)}
                            label="Gender"
                        >
                            <MenuItem value=""><em>None</em></MenuItem>
                            <MenuItem value="MALE">Male</MenuItem>
                            <MenuItem value="FEMALE">Female</MenuItem>
                            <MenuItem value="OTHER">Other</MenuItem>
                        </Select>
                    </FormControl>
                    <div className="flex justify-start mt-5">
                        <Button
                            variant="contained"
                            type="submit"
                            sx={{
                                backgroundColor: "#AEC761",
                                borderRadius: "20px",
                                textTransform: "none",
                                "&:hover": {
                                    backgroundColor: "#9BBF5A"
                                }
                            }}
                        >
                            Sign Up
                        </Button>
                    </div>
                    <div className="mt-5 text-center">
                        <p>Already have an account? <Link to="/login" className="text-blue-500">Log In</Link></p>
                    </div>
                </form>
            </Box>
        </div>
    );
};