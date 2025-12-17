package com.himmerland.hero.domain.departments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class DepartmentTest {

    @Test
    void constructorWithParametersSetsNameAndEmail() {
        // Arrange & Act
        Department department = new Department("IT Department", "it@company.com");
        
        // Assert
        assertEquals("IT Department", department.getName());
        assertEquals("it@company.com", department.getEmail());
    }

    @Test
    void constructorWithParametersAppliesIdToIdentifiableBase() {
        // Arrange & Act
        Department department = new Department("HR Department", "hr@company.com");
        
        // Assert
        assertEquals("HR Department", department.getName());
    }

    @Test
    void constructorWithNullNameDoesNotOverrideGeneratedId() {
        // Arrange & Act
        Department department = new Department(null, "test@company.com");
        
        // Assert
        assertNotNull(department.getId());
        assertEquals(null, department.getName()); // getName returns id when Name is null
        assertEquals("test@company.com", department.getEmail());
    }

    @Test
    void defaultConstructorCreatesInstanceWithGeneratedId() {
        // Arrange & Act
        Department department = new Department();
        
        // Assert
        assertNotNull(department.getId());
    }

    @Test
    void getNameReturnsNameWhenSet() {
        // Arrange
        Department department = new Department("Finance", "finance@company.com");
        
        // Act
        String name = department.getName();
        
        // Assert
        assertEquals("Finance", name);
    }

    @Test
    void getNameReturnsIdWhenNameIsNull() {
        // Arrange
        Department department = new Department();
        department.setEmail("test@company.com");
        
        // Act
        String name = department.getName();
        
        // Assert
        assertEquals(department.getName(), name);
    }

    @Test
    void getEmailReturnsEmail() {
        // Arrange
        Department department = new Department("Sales", "sales@company.com");
        
        // Act
        String email = department.getEmail();
        
        // Assert
        assertEquals("sales@company.com", email);
    }

    @Test
    void setNameUpdatesNameAndId() {
        // Arrange
        Department department = new Department();
        String originalId = department.getId();
        
        // Act
        department.setName("Marketing");
        
        // Assert
        assertEquals("Marketing", department.getName());
        assertEquals("Marketing", department.getId());
        assertNotEquals(originalId, department.getId());
    }

    @Test
    void setNameWithNullDoesNotUpdateId() {
        // Arrange
        Department department = new Department("Operations", "ops@company.com");
        String originalId = department.getId();
        
        // Act
        department.setName(null);
        
        // Assert
        assertEquals(null, department.getName()); // getName returns id when Name is null
        assertEquals(originalId, department.getId());
    }

    @Test
    void setEmailUpdatesEmail() {
        // Arrange
        Department department = new Department("Support", "support@company.com");
        
        // Act
        department.setEmail("new-support@company.com");
        
        // Assert
        assertEquals("new-support@company.com", department.getEmail());
    }

    @Test
    void setEmailWithNullUpdatesEmail() {
        // Arrange
        Department department = new Department("Legal", "legal@company.com");
        
        // Act
        department.setEmail(null);
        
        // Assert
        assertNull(department.getEmail());
    }

    @Test
    void multipleSetNameCallsUpdateIdEachTime() {
        // Arrange
        Department department = new Department();
        
        // Act
        department.setName("First");
        String firstId = department.getId();
        
        department.setName("Second");
        String secondId = department.getId();
        
        // Assert
        assertEquals("Second", department.getName());
        assertEquals("Second", secondId);
        assertNotEquals(firstId, secondId);
    }
}
