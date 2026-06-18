# StockSnap – Master Project Specification

You are a senior Android engineer.

Read the entire project specification below before generating any code.

## Rules

* Follow the specification exactly.
* Do not add features not mentioned in the specification.
* Use Kotlin, Jetpack Compose, Material 3, MVVM, Hilt, Room, CameraX, ML Kit OCR, ML Kit Barcode Scanner, and ZXing.
* The app must be completely offline.
* No backend, cloud, Firebase, login, or internet features.
* Build production-quality code.
* Generate files incrementally and maintain clean architecture.
* If context becomes too large, summarize progress and provide the next implementation step.
* Before generating code, explain the architecture and files that will be created.

## Primary Goal

Minimize employee interaction.

The employee should ideally be able to:

1. Take 3 photos.
2. Verify extracted details.
3. Save.
4. Mark Updated after POS entry.

Every additional tap should be questioned.

---

# Project: StockSnap

## Overview

StockSnap is a fully offline Android application designed for retail store employees to digitize newly arrived products before updating them in existing POS software.

The app is NOT an inventory management system.

The app is NOT connected to a server, cloud, internet, or POS.

The app acts as a smart offline assistant that:

* Captures product information using the phone camera.
* Extracts product details using offline OCR and barcode recognition.
* Stores all data locally on the device.
* Maintains a daily checklist of products that need to be updated in the store's POS software.
* Allows employees to mark products as "Updated" after entering them into the POS.

---

# Core Problem

Current workflow:

New products arrive.

Employee physically carries products to POS computer.

Employee manually enters:

* Product Name
* Barcode
* MRP

Employee repeats for every product.

StockSnap improves this workflow by allowing employees to scan products in the storage area and use the app later while updating the POS.

---

# Target Users

* Grocery stores
* Department stores
* Supermarkets
* Medical shops
* Retail outlets
* Wholesale distributors

---

# Offline Requirement

The application must work completely offline.

* No internet required.
* No cloud services.
* No backend.
* No login.
* No account creation.
* No synchronization.

All data remains on the device.

---

# Tech Stack

## Android

* Kotlin
* Jetpack Compose
* Material 3

## Architecture

* MVVM
* Dependency Injection
* Hilt

## Camera

* CameraX

## OCR

* Google ML Kit Text Recognition (Offline)

## Barcode Detection

* Google ML Kit Barcode Scanner (Offline)

## Database

* Room Database

## Background Tasks

* WorkManager

## QR Generation

* ZXing

---

# UI Design Philosophy

Inspired by Birday:

* Minimalistic
* Clean cards
* Large spacing
* Material 3
* Dark mode support
* Very few screens
* Fast navigation
* No clutter

---

# App Navigation

Bottom Navigation Only

1. Dashboard
2. History
3. Settings

---

# Product Capture Flow

## Step 1

Capture Front Image

Purpose:

* Product image
* Product name extraction
* Brand extraction

## Step 2

Capture Barcode Image

Purpose:

* Barcode extraction

## Step 3

Capture MRP Image

Purpose:

* MRP extraction
* Weight extraction

## Step 4

Process Images

The application runs OCR and barcode recognition.

Extracted fields:

* Product Name
* Barcode Number
* MRP
* Brand
* Weight

## Step 5

Review Screen

User verifies extracted values.

Editable:

* Product Name
* MRP
* Brand
* Weight
* Quantity

Locked:

* Barcode

Barcode should never be editable after successful recognition.

---

# Dashboard

Shows today's work.

Example:

Today's Scans

Total: 42

Updated: 30

Pending: 12

Button:

* New Scan

---

# Today's Scans List

Example:

● Pending
Aachi Chicken Masala

✓ Updated
Good Day Biscuit

● Pending
Coca Cola

Status values:

* Pending
* Updated

---

# Product Status Workflow

Newly scanned products:

Pending

After employee updates POS:

Updated

Employee manually toggles status.

No automatic POS integration.

---

# Product Details Screen

Fields:

* Product Image
* Product Name
* Barcode Number
* QR Code
* MRP
* Quantity
* Status
* Created Date

---

# QR Code Feature

Generate QR code from:

Barcode Number

Example:

8901234567890

QR contains only the barcode number.

Purpose:

Employee can display QR on phone and attempt to scan it using the POS scanner.

Some POS scanners may not support QR.

Feature remains optional.

---

# MRP Change Detection

When barcode already exists:

Compare:

* Old MRP
* New MRP

Example:

Old MRP ₹40

New MRP ₹45

Display:

MRP Changed

Highlight the difference.

---

# Daily Scan Sessions

Products are grouped by date.

Example:

17 Jun 2026 → 42 Products

16 Jun 2026 → 35 Products

15 Jun 2026 → 27 Products

---

# History Screen

Shows previous scan sessions.
Selecting a session opens all products scanned that day.

---

# Database Design

## Product Entity

```kotlin
@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val barcode: String,

    val mrp: Double,

    val brand: String?,

    val weight: String?,

    val quantity: Int?,

    val frontImagePath: String,

    val barcodeImagePath: String,

    val mrpImagePath: String,

    val status: ProductStatus,

    val createdAt: Long,

    val updatedAt: Long
)
```

## ProductStatus

```kotlin
enum class ProductStatus {
    PENDING,
    UPDATED
}
```

---

# OCR Extraction Rules

Product Name:

* Extract from front image

MRP:

* Extract from MRP image

Barcode:

* Extract from barcode image

Weight:

* Optional extraction

Brand:

* Optional extraction

# Image Storage

Store images locally.

Example:

Android/data/com.stocksnap/images/

Room stores file paths only.

Do not store image blobs in database.

# Main Screens

## Dashboard

Shows daily statistics.

## New Scan

Capture three images.

## Review Product

Edit extracted information.

## Today's Scans

List of products scanned today.

## Product Details

Detailed view.

## History

Past sessions.

## Settings

* Dark mode
* Data export
* About

# Features Explicitly Excluded

Do NOT implement:

❌ Login

❌ Registration

❌ Backend

❌ API

❌ Cloud sync

❌ Firebase

❌ POS integration

❌ User accounts

❌ Notifications

❌ Online AI

❌ Chatbot

❌ Inventory management

❌ Multi-store support

❌ Supplier management

❌ Analytics dashboard

# MVP Goal

The employee should be able to:

1. Open app.
2. Capture Front image.
3. Capture Barcode image.
4. Capture MRP image.
5. Automatically extract details.
6. Review and edit details.
7. Save product.
8. See product in Today's Scans.
9. Update product in POS manually and mark as Updated.

If these 9 actions work smoothly, Version 1 is complete.

# Suggested Package Structure

```text
com.stocksnap

├── data
│   ├── database
│   ├── repository
│
├── domain
│   ├── model
│   ├── usecase
│
├── presentation
│   ├── dashboard
│   ├── scan
│   ├── review
│   ├── details
│   ├── history
│   ├── settings
│
├── camera
├── ocr
├── barcode
├── qr
├── navigation
├── di
```

# Recommended Development Order

Week 1:

* Project setup
* Hilt
* Navigation
* Room Database

Week 2:

* Dashboard
* Product CRUD
* Today's Scans

Week 3:

* CameraX integration
* Three-image capture flow

Week 4:

* ML Kit OCR
* Barcode Scanner
* Review Screen

Week 5:

* Product Details
* QR Generation
* History
* MRP Change Detection
* Polish and testing

Start implementation with:

Dashboard → Capture 3 Images → Review → Save

This flow is the highest priority MVP.

This document is saved as `docs/PRD.md`.
