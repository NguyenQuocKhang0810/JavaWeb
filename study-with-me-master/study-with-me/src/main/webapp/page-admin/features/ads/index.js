$(document).ready(function() {

	var $pagination = $('#postsPagination');
	var inpSearchPostsName = '';
	// Init twbsPagination with defaultOpts /assets/global.settings.js)
	$pagination.twbsPagination(defaultOpts);

	// Listener event onChange when user typing on input search.
	this.onSearchByName = function() {
		// Get value from input search.
		inpSearchPostsName = $('#inpSearchPostsName').val();
		// Call function search filter value from input.
		this.getPosts(0, defaultPageSize, inpSearchPostsName);
	}

	// Show images in modal
	window.showImages = function(imageUrls) {
		const carouselInner = $('#carouselInner').empty();
		imageUrls.forEach((url, index) => {
			carouselInner.append(`
                <div class="carousel-item ${index === 0 ? 'active' : ''}">
                    <img src="${url}" class="d-block w-100" alt="Image">
                </div>
            `);
		});
		$('#imageModal').modal('show');
	};

	// Function search and pagination Posts. 
	this.getPosts = function(page = 0, size = defaultPageSize, name = '') {
		// Use Ajax call API search posts (/assets/http.js).
		Http.get(`${domain}/admin/api/ads?type=filter&page=${page}&size=${size}&name=${name}`)
			.then(res => {
				let appendHTML = '';
				// Clear all elements in table content.
				$('#tblPosts').empty();
				// Reset pagination.
				$pagination.twbsPagination('destroy');
				// Check api error or no data response.
				if (!res.success || res.data.totalRecord === 0) {
					// Append text No Data when records empty;
					$('#tblPosts').append(`<tr><td colspan='9' style='text-align: center;'>No Data</td></tr>`);
					// End function.
					return;
				}

				// Build table content from data responses.
				for (const record of res.data.records) {
					appendHTML += '<tr>';
					appendHTML += `<td>${record.id}</td>`;
					appendHTML += `<td>${record.position}</td>`;
					appendHTML += `<td>${record.width}</td>`;
					appendHTML += `<td>${record.height}</td>`;
					appendHTML +=
						`<td>
            <span class='badge ${record.status.toLowerCase() === 'active' ? 'bg-success' : 'bg-danger'}'>
                ${record.status}
            </span>
        </td>`;
					appendHTML += `<td>${record.url}</td>`;

					// Kiểm tra xem images có phải là null hoặc trống không
					let imageLists = record.images ? record.images.split(',') : [];
					let imagesHTML = imageLists.map(img => `<div>${img.trim()}</div>`).join('');
					appendHTML += `<td>${imagesHTML}</td>`;
					appendHTML += `<td>${record.updatedDate}</td>`;

					// Append action button Edit & Delete.
					appendHTML +=
						`<td class='text-right'>
            <a class='btn btn-info btn-sm' href='#' 
                onclick='showImages([${imageLists.map(img => `"${domain}/public/${img.trim()}"`).join(', ')}])'>
                <i class="fas fa-image"></i>
            </a>
            <a class='btn btn-info btn-sm' onclick='swicthViewPosts(false, ${record.id})'>
                <i class='fas fa-pencil-alt'></i>
            </a>
            <a class='btn btn-danger btn-sm' onclick='deletePosts(${record.id})'>
                <i class='fas fa-trash'></i>
            </a>
        </td>`;
					appendHTML += '</tr>';
				}

				// Build pagination with twbsPagination.
				// More detail: https://josecebe.github.io/twbs-pagination/
				$pagination.twbsPagination($.extend({}, defaultOpts, {
					startPage: res.data.page + 1,
					totalPages: Math.ceil(res.data.totalRecord / res.data.size)
				}));
				// Add event listener when page change.
				$pagination
					.on('page', (event, num) => {
						this.getPosts(num - 1, defaultPageSize, inpSearchPostsName);
					});

				// Append html table into tBody.
				$('#tblPosts').append(appendHTML);
			})
			.catch(err => {
				toastr.error(err.errMsg);
			})
	}

	// Function delete posts by id.
	this.deletePosts = function(id) {
		// Show up popup
		if (confirm("Are you sure you want to delete this advertisement?")) {
			// If confirm then delete ads
			Http.delete(`${domain}/admin/api/ads?id=${id}`)
				.then(res => {
					if (res.success) {
						this.swicthViewPosts(true);
						toastr.success('Delete ads success!');
					} else {
						toastr.error(res.errMsg);
					}
				})
				.catch(err => {
					toastr.error(err.errMsg);
				});
		} else {
		}
	}


	// Call API get posts by id.
	this.getPostsById = function(id) {
		// Use Ajax call API get posts by id (/assets/http.js).
		Http.get(`${domain}/admin/api/ads?type=getOne&id=${id}`)
			.then(res => {
				if (res.success) {
					// Set value from response on update form.
					$('#inpPostsId').val(id);
					$('#inpPostsBanner').val(null);
					$('#inpPostsTitle').val(res.data.position);
					$('#inpWidthTitle').val(res.data.width);
					$('#inpHeightTitle').val(res.data.height);

					$('#inpUrlTitle').val(res.data.url);
					// Set value for box selects category.

					// Set value for textarea Content.

				} else {
					toastr.error(res.errMsg);
				}
			})
			.catch(err => {
				toastr.error(err.errMsg);
			})
	}

	// Function create/edit posts.
	this.savePosts = function() {
		const currentId = $('#inpPostsId').val();
		const payload = {
			'position': $('#inpPostsTitle').val(),
			'width': $('#inpWidthTitle').val(),
			'height': $('#inpHeightTitle').val(),
			'url': $('#inpUrlTitle').val()
		};

		var formData = new FormData();
		// Append multiple files
		const files = $('#inpPostsBanner')[0].files;
		for (let i = 0; i < files.length; i++) {
			formData.append('images', files[i]);
		}

		// Append payload
		formData.append('payload', JSON.stringify(payload));

		const url = currentId?`${domain}/admin/api/ads?id=${currentId}`:`${domain}/admin/api/ads`;
		const method = currentId ? Http.putFormData : Http.postFormData;

		method(url, formData)
			.then(res => {
				if (res.success) {
					this.swicthViewPosts(true);
					toastr.success(currentId ? 'Update posts success!' : 'Create posts success!');
				} else {
					toastr.error(res.errMsg);
				}
			})
			.catch(err => {
				toastr.error(err.errMsg);
			});
	};

	// TODO: Handle after.
	this.draftPosts = function() {
		alert("Code	");
	}

	// Using select2 query data categories.
	this.initSelect2Category = function() {
		// Init value for select2 on id #selPostsCategory.
		$('#selPostsCategory').select2({
			theme: 'bootstrap4',
			// Call api search category with select2.
			ajax: {
				url: `${domain}/admin/api/category`,
				headers: {
					// Get token from localStore and append on API.
					// Read more function: /assets/http.js
					'Authorization': 'Bearer ' + Http.getToken(),
					'Content-Type': 'application/json',
				},
				data: function(params) {
					var query = {
						type: 'filter',
						page: 0,
						size: 10,
						// params.term is value input on select2.
						name: params.term
					}
					// Query parameters will be ?type=[type]&page=[page]&size=[size]&name=[params.term]
					return query;
				},
				// Transform the data returned by your API into the format expected by Select2
				// Default format when use select2 is [{id: [id], text: [text]}]
				// So we need convert data from response to format of select2.
				processResults: function(res) {
					return {
						results: res.data.records.map(elm => {
							return {
								id: elm.id,
								text: elm.name
							}
						})
					};
				}
			}
		});
	}

	// Action change display screen between Table and Form Create/Edit.
	this.swicthViewPosts = function(isViewTable, id = null) {
		if (isViewTable) {
			$('#posts-table').css('display', 'block');
			$('#posts-form').css('display', 'none');
			this.getPosts(0, defaultPageSize);
		} else {
			// Init summernote (Text Editor).
			$('#inpPostContent').summernote({ height: 150 });
			// Init select2 (Support select & search value).
			this.initSelect2Category();
			$('#posts-table').css('display', 'none');
			$('#posts-form').css('display', 'block');
			if (id == null) {
				$('#inpPostsId').val(null);
				$('#inpPostsTitle').val(null);
				$('#inpWidthTitle').val(null);
				$('#inpHeightTitle').val(null);
				$('#inpPostsBanner').val(null);
				$('#inpUrlTitle').val(null);
			} else {
				this.getPostsById(id);
			}
		}
	};

	this.moveSlide = function(listImg) {
		if (listImg == null) {
			this.next('')
		}
		const slides = document.querySelectorAll('.slides img');
		slideIndex = (slideIndex + n + slides.length) % slides.length;
		const offset = -slideIndex * 100;
		document.querySelector('.slides').style.transform = `translateX(${offset} %)`;
	}

	// Fix issues Bootstrap 4 not show file name.

	$('#inpPostsBanner').change(function(e) {
		let fileNames = Array.from(e.target.files).map(file => file.name).join(', ');
		$(this).next('.custom-file-label').html(fileNames);
	});

	// Set default view mode is table.
	this.swicthViewPosts(true);

});