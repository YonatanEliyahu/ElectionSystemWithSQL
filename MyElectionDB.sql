CREATE SCHEMA ELECTION;
USE ELECTION;


CREATE TABLE Citizen
(Citizen_ID INT NOT NULL,
First_Name VARCHAR(50),
Birth_Year INT,
Age INT,
PRIMARY KEY (Citizen_ID)) 
ENGINE = InnoDB; 

CREATE TABLE Ballot
(Ballot_ID INT NOT NULL,
Address VARCHAR(50),
PRIMARY KEY (Ballot_ID))
ENGINE = InnoDB; 


CREATE TABLE Party
(Party_Name VARCHAR(50) NOT NULL,
Side VARCHAR(50),
Start_Date date,
PRIMARY KEY (Party_Name))
ENGINE = InnoDB; 


CREATE TABLE Soldier
(CID INT	 not null,
Carry_wep bool default false,
primary key(CID),
FOREIGN KEY (CID) REFERENCES Citizen(Citizen_ID))
ENGINE = InnoDB; 

 

CREATE TABLE Vote_For  
(CID INT NOT NULL,
PName VARCHAR(50) not null,
FOREIGN KEY (CID) REFERENCES Citizen(Citizen_ID),
FOREIGN KEY (PName) REFERENCES Party(Party_Name))
ENGINE = InnoDB; 


CREATE TABLE Vote_In
(CID INT NOT NULL,
BID INT NOT NULL,
FOREIGN KEY (CID) REFERENCES Citizen(Citizen_ID),
FOREIGN KEY (BID) REFERENCES Ballot(Ballot_ID))
ENGINE = InnoDB; 


CREATE TABLE Candidate_For
(CID INT NOT NULL,
PNAME VARCHAR(50) NOT NULL,
FOREIGN KEY (CID) REFERENCES Citizen(Citizen_ID),
FOREIGN KEY (PName) REFERENCES Party(Party_Name))
ENGINE = InnoDB; 


USE ELECTION;

INSERT INTO Citizen VALUES
-- (Citizen_ID, First_Name, Birth_Year, Age)
-- (   INT ,   String ,     INT,      INT)
(308006501, 'Babu', 1990, 32),
(209006502, 'Shani', 1995, 26),
(209006503, 'Karin', 1995, 26),
(209006504, 'Hola', 1994, 27),
(209006505, 'Donya', 1995, 26),
(209006506, 'Max', 1997, 24),
(209006507, 'Ori', 1990, 32),
(209006508, 'Yoni', 1997, 24),
(209006509, 'Shimon', 1997, 24),
(209006510, 'Alex', 1997, 24),
(209006011, 'Maor', 1997, 24),
(666666012, 'Noy', 1990, 32),
(666666013, 'Amit', 1976, 46),
(666666014, 'Tal', 1976, 46),
(666666015, 'Yuval', 1990, 32),
(666666016, 'Scarlet', 1976, 46),
(123456017, 'Yasmin', 1997, 24),
(100000018, 'Buki', 1976, 46), 
(100000019, 'Rasta', 1976, 46), 
(100000020, 'Mano', 1970, 52), 
(100000021, 'Neri', 1970, 52), 
(100000022, 'Linik', 1970, 52), 
-- candidates
(340000023, 'Rami', 1975, 47), 
(300000024, 'Ahmad', 1975, 47), 
(500000025, 'Roman', 1976, 46),
(600000026, 'Asher', 1976, 46),
(700000027, 'Maman', 1977, 45), 
(100000028, 'Eliraz', 1977, 45),
(800000029, 'Meretz', 1988, 34),
(100000029, 'Arik', 1977, 45), 
(100000030, 'Amri', 1976, 46), 
(100000031, 'Afik', 1976, 46),
(209355032, 'Avi', 1941, 81), 
(408689033, 'Yair', 1940, 82),
(508689034, 'Lavi', 1940, 82),

-- Soldiers
(308689031, 'Rami', 2001, 20),
(208689037, 'Itzhak', 2002, 19),
(208689038, 'Moti', 2001, 20),
(323456039, 'Yarden', 2002, 19),
(223456040, 'Rafi', 2001, 20),
(723456041, 'Ron', 2001, 20),
(523456042, 'Shimi', 2002, 19);

INSERT INTO Ballot VALUES
-- (Ballot_ID, ADDRESS)
(1, 'Haifa'),
(2, 'Tel-Aviv'),
(3, 'Zefat'),
(4, 'Ashkelon');

INSERT INTO Party VALUES
('Licud', 'Left', "2021-11-13"),
('Meretz', 'Left', "2021-12-03"),
('Kahol Lavan', 'Left', "2021-03-07"),
('Yesh Atid', 'Left', "2021-03-06"),
('Havoda', 'Left', "2021-03-09");

INSERT INTO Soldier VALUES
(308689031, False), -- Rami
(208689037, False), -- Itzhak
(208689038, True), -- Moti
(323456039, True), -- Yarden
(223456040, True), -- Rafi
(723456041, True), -- Ron
(523456042, True ); -- Shimi

INSERT INTO Candidate_For VALUES
(340000023, 'Licud'), -- Rami
(300000024, 'Licud'), -- Ahmad
(500000025, 'Licud'), -- Roman
(600000026, 'Licud'), -- Asher
(700000027, 'Meretz'), -- Maman
(100000028, 'Meretz'), -- Eliraz  
(800000029, 'Meretz'), -- Abu Dalal
(100000030, 'Kahol Lavan'), -- Amri
(100000031, 'Kahol Lavan'), -- Afik
(209355032, 'Kahol Lavan'), -- Avi
(408689033, 'Havoda'); -- Yair


INSERT INTO Vote_For VALUES
-- Candidates votes
(340000023, 'Meretz'), -- Rami Licud + Traitor candidate for Meretz
(300000024, 'Havoda'), -- Ahmad Licud + Traitor candidate for Havoda
(500000025, 'Licud'), -- Roman
(600000026, 'Licud'), -- Asher
(700000027, 'Meretz'), -- Maman
(100000028, 'Meretz'), -- Eliraz  
(800000029, 'Meretz'), -- Abu Dalal
(100000030, 'Licud'), -- Amri Kahol Lavan + Traitor candidate for Licud
(100000031, 'Kahol Lavan'), -- Afik
(209355032, 'Kahol Lavan'), -- Avi
(408689033, 'Havoda'), -- Yair
-- Regular Citizens votes
(308006501, 'Licud'),
(209006502, 'Licud'),
(209006503, 'Licud'),
(209006504, 'Licud'),
(209006505, 'Licud'),
(209006506, 'Licud'),
(209006507, 'Meretz'),
(209006508, 'Meretz'),
(209006509, 'Meretz'),
(209006510, 'Meretz'),
(209006011, 'Havoda'),
(666666012, 'Havoda'),
(666666013, 'Kahol Lavan'),
(666666014, 'Kahol Lavan'),
(666666015, 'Kahol Lavan'),
(666666016, 'Yesh Atid'),
(123456017, 'Yesh Atid'),
(100000018, 'Yesh Atid'), 
(100000019, 'Yesh Atid'), 
(100000020, 'Yesh Atid'), 
(100000021, 'Yesh Atid'), 
(100000022, 'Yesh Atid'),
-- Soldiers
(308689031, 'Licud'), 
(208689037, 'Licud'),
(208689038, 'Licud'),
(323456039, 'Meretz'),
(223456040, 'Meretz'),
(723456041, 'Meretz'),
(523456042, 'Meretz');

INSERT INTO Vote_In VALUES
-- regular citizens
(308006501, 1),
(209006502, 1),
(209006503, 1),
(209006504, 1),
(209006505, 1),
(209006506, 1),
(209006507, 1),
(209006508, 2),
(209006509, 2),
(209006510, 2),
(209006011, 2),
(666666012, 3),
(666666013, 3),
(666666014, 3),
(666666015, 3),
(666666016, 3),
(123456017, 4),
(100000018, 4), 
(100000019, 4), 
(100000020, 4), 
(100000021, 4), 
(100000022, 4), 
(100000029, 3), 
-- candidates
(340000023, 1), 
(300000024, 1), 
(500000025, 1),
(508689034, 2),
(600000026, 2),
(700000027, 2), 
(100000028, 2),   
(800000029, 3), 
(100000030, 3),
(100000031, 4), 
(209355032, 4),
(408689033, 4),
-- Soldiers
(308689031, 2), 
(208689037, 3), 
(208689038, 4),
(223456040, 4), 
(323456039, 1),
(723456041,2),
(523456042, 1); 
