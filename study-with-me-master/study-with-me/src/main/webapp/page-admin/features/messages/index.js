$(document).ready(function() {

	var $pagination = $('#messagesPagination');
	var inpSearchMessagesName = '';

	// Init twbsPagination with defaultOpts /assets/global.settings.js
	$pagination.twbsPagination(defaultOpts);

	// Listener event onChange when user typing on input search.
	this.onSearchByName = function() {
		// Get value from input search.
		inpSearchMessagesName = $('#inpSearchMessagesName').val();
		// Call function search filter value from input.
		this.getMessages(0, defaultPageSize, inpSearchMessagesName);
	}

	// Function to format date as "a few minutes ago"
	function formatDate(dateString) {
		return moment(dateString).fromNow();
	}

	// Function search and pagination Messages.
	this.getMessages = function(page = 0, size = defaultPageSize, name = '') {
		// Use Ajax call API search messages (/assets/http.js).
		Http.get(`${domain}/admin/api/messages?type=filter&page=${page}&size=${size}&name=${name}`)
			.then(res => {
				let appendHTML = '';
				// Clear all elements in table content.
				$('#tblMessages').empty();
				// Reset pagination.
				$pagination.twbsPagination('destroy');
				// Check api error or no data response.
				if (!res.success || res.data.totalRecord === 0) {
					// Append text No Data when records empty.
					$('#tblMessages').append(`<tr><td colspan='10' style='text-align: center;'>No Data</td></tr>`);
					// End function.
					return;
				}

				// Build table content from data responses.
				for (let i = res.data.records.length - 1; i >=0; i--) {
					const record = res.data.records[i];
					appendHTML += '<tr>';
					appendHTML += `<td>${record.id}</td>`;
					appendHTML += `<td>${record.email}</td>`;
					appendHTML += `<td>${record.subject}</td>`;
					appendHTML += `<td>${record.message}</td>`;
					appendHTML += `<td>${formatDate(record.createdDate)}</td>`;
					// Append action button Edit & Delete.
					appendHTML +=
						`<td class='text-right'>
                            <a class='btn btn-danger btn-sm' onclick='deleteMessages(${record.id})'>
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
						this.getMessages(num - 1, defaultPageSize, inpSearchMessagesName);
					});

				// Append html table into tBody.
				$('#tblMessages').append(appendHTML);
			})
			.catch(err => {
				toastr.error(err.errMsg);
			})
	}

	// Function delete messages by id.
	this.deleteMessages = function(id) {
		// Use Ajax call API delete messages by id (/assets/http.js).
		if (confirm('Are you sure you want to delete this message?')) {
			Http.delete(`${domain}/admin/api/messages?id=${id}`)
				.then(res => {
					if (res.success) {
						this.switchViewMessages(true);
						toastr.success('Delete messages success !')
					} else {
						toastr.error(res.errMsg);
					}
				})
				.catch(err => {
					toastr.error(err.errMsg);
				})
		}
	}

	// Confirm before delete

	// Call API get messages by id.
	this.getMessagesById = function(id) {
		// Use Ajax call API get messages by id (/assets/http.js).
		Http.get(`${domain}/admin/api/messages?type=getOne&id=${id}`)
			.then(res => {
				if (res.success) {
					// Set value from response on update form.
					$('#inpMessagesId').val(id);
					$('#inpMessagesSubject').val(res.data.subject);
					$('#inpMessagesEmail').val(res.data.email);
					$('#inpMessagesContent').val(res.data.message);
					// Set value for box selects category.
					if ($('#selMessagesCategory').find("option[value='" + res.data.categoryId + "']").length) {
						$('#selMessagesCategory').val(res.data.categoryId).trigger('change');
					} else {
						// Create a DOM Option and pre-select by default
						var newOption = new Option(res.data.categoryName, res.data.categoryId, true, true);
						// Append it to the select
						$('#selMessagesCategory').append(newOption).trigger('change');
					}
					////
				} else {
					toastr.error(res.errMsg);
				}
			})
			.catch(err => {
				toastr.error(err.errMsg);
			})
	}

	// Function create/edit messages.
	this.saveMessages = function() {
		const currentId = $('#inpMessagesId').val();
		// Get value from input and build a JSON Payload.
		const payload = {
			'subject': $('#inpMessagesSubject').val(),
			'email': $('#inpMessagesEmail').val(),
			'message': $('#inpMessagesContent').val(),
		}
		// Create FormData and append files & JSON stringify.
		var formData = new FormData();
		// Append payload messages info.
		formData.append('payload', JSON.stringify(payload));
		if (currentId) {
			// Read detail additional function putFormData in file: /assets/http.js
			Http.putFormData(`${domain}/admin/api/messages?id=${currentId}`, body)
				.then(res => {
					if (res.success) {
						this.switchViewMessages(true);
						toastr.success(`Update messages success !`)
					} else {
						toastr.error(res.errMsg);
					}
				})
				.catch(err => {
					toastr.error(err.errMsg);
				});
		} else {
			// Read detail additional function postFormData in file: /assets/http.js
			Http.postFormData(`${domain}/admin/api/messages`, body)
				.then(res => {
					if (res.success) {
						this.switchViewMessages(true);
						toastr.success(`Create messages success !`)
					} else {
						toastr.error(res.errMsg);
					}
				})
				.catch(err => {
					toastr.error(err.errMsg);
				});
		}
	};

	// TODO: Handle after.
	this.draftMessages = function() {
		alert("Làm biếng chưa có code");
	}

	// Using select2 query data categories.
	this.initSelect2Category = function() {
		// Init value for select2 on id #selMessagesCategory.
		$('#selMessagesCategory').select2({
			theme: 'bootstrap4',
			// Call api search category with select2.
			ajax: {
				url: `${domain}/admin/api/category`,
				headers: {
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
	this.switchViewMessages = function(isViewTable, id = null) {
		if (isViewTable) {
			$('#messages-table').css('display', 'block');
			$('#messages-form').css('display', 'none');
			this.getMessages(0, defaultPageSize);
		} else {
			// Init summernote (Text Editor).
			$('#inpMessagesContent').summernote({ height: 150 });
			// Init select2 (Support select & search value).
			this.initSelect2Category();
			$('#messages-table').css('display', 'none');
			$('#messages-form').css('display', 'block');
			if (id == null) {
				$('#inpMessagesSubject').val(null);
				$('#inpMessagesEmail').val(null);
				$('#inpMessagesContent').val('');
			} else {
				this.getMessagesById(id);
			}
		}
	};

	// Fix issues Bootstrap 4 not show file name.
	$('#inpMessagesBanner').change(function(e) {
		if (e.target.files.length) {
			// Replace the "Choose a file" label
			$(this).next('.custom-file-label').html(e.target.files[0].name);
		}
	});

	// Set default view mode is table.
	this.switchViewMessages(true);

});
