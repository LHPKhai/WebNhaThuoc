CREATE DATABASE PharmacyDB;
GO

USE PharmacyDB;
GO
CREATE TABLE [User] (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    Username NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    PhoneNumber NVARCHAR(20),
    Address NVARCHAR(255),
    Role NVARCHAR(50) CHECK (Role IN ('Admin', 'Pharmacist', 'Customer'))
);
CREATE TABLE Product (
    ProductID INT PRIMARY KEY IDENTITY(1,1),
    ProductName NVARCHAR(200) NOT NULL,
    Price DECIMAL(10, 2) CHECK (Price >= 0),
    Indications NVARCHAR(1000),
    ProductionDate DATE,
    ExpiryDate DATE,
    Description NVARCHAR(1000),
    Manufacturer NVARCHAR(200),
    Contraindication NVARCHAR(1000)
);
CREATE TABLE [Order] (
    OrderID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT NOT NULL FOREIGN KEY REFERENCES [User](UserID),
    OrderDate DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(50),
    Description NVARCHAR(1000)
);
CREATE TABLE OrderItem (
    OrderItemID INT PRIMARY KEY IDENTITY(1,1),
    OrderID INT NOT NULL FOREIGN KEY REFERENCES [Order](OrderID),
    ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID),
    Quantity INT NOT NULL CHECK (Quantity > 0),
    UnitPrice DECIMAL(10, 2) NOT NULL CHECK (UnitPrice >= 0)
);
CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY IDENTITY(1,1),
    OrderID INT NOT NULL FOREIGN KEY REFERENCES [Order](OrderID),
    PaymentMethod NVARCHAR(100),
    Price DECIMAL(10, 2) NOT NULL CHECK (Price >= 0),
    PaymentDate DATETIME DEFAULT GETDATE()
);
CREATE TABLE Comment (
    CommentID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT NOT NULL FOREIGN KEY REFERENCES [User](UserID),
    ProductID INT NOT NULL FOREIGN KEY REFERENCES Product(ProductID),
    Content NVARCHAR(1000),
    Stars INT CHECK (Stars BETWEEN 1 AND 5),
    CommentDate DATETIME DEFAULT GETDATE()
);
CREATE TABLE Notification (
    NotificationID INT PRIMARY KEY IDENTITY(1,1),
    CreatorID INT NOT NULL FOREIGN KEY REFERENCES [User](UserID),
    Message NVARCHAR(1000) NOT NULL,
    DateTime DATETIME DEFAULT GETDATE()
);

CREATE TABLE NotificationRecipient (
    NotificationID INT NOT NULL FOREIGN KEY REFERENCES Notification(NotificationID),
    RecipientID INT NOT NULL FOREIGN KEY REFERENCES [User](UserID),
    PRIMARY KEY (NotificationID, RecipientID)
);
CREATE TABLE Address (
    AddressID INT PRIMARY KEY IDENTITY(1,1),
    ProvinceOrCity NVARCHAR(100),
    District NVARCHAR(100),
    CommuneOrWardOrTown NVARCHAR(100),
    Street NVARCHAR(200),
    HouseNumber NVARCHAR(50)
);
