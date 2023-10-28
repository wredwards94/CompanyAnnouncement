interface Announcement {
    title: string;
    message: string;
    author: User;
}

interface NewAnnouncement extends Announcement {
    companyName?: string;
}

interface DisplayAnnouncement extends Announcement {
    id: number;
    date: string;
}

type UserCredentials = {
    username: string;
    password: string;
}

type UserProfile = {
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
};

type User = {
    id?: number;
    profile: UserProfile;
    credentials: UserCredentials;
    admin: boolean;
    active: boolean;
    status: string;
    companies?: Company[];
    teams?: Team[];
};

type Company = {
    id: number;
    name: string;
    description: string;
    teams: Team[];
    users: User[];
};

type Team = {
    id: number;
    name: string;
    description: string;
    users: User[];
};

type Project = {
    id?: number;
    name: string;
    description: string;
    active: boolean;
    team: Team;
};

type Credentials = {
    username: string; 
    password: string;
}

type NewUser = {
    credentials: Credentials;
    profile: UserProfile;
    admin: boolean;
}
